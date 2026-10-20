# Runtime memory rules

---

Address lifetimes

Every `GamePointer` declares a `Lifetime`. It says how long the address it resolves to stays
valid, and therefore when a cached resolution has to be thrown away.

| Tier | What lives here | Invalidated by |
|---|---|---|
| `Process` | Module bases, addresses signatures resolve to | Process exit |
| `Session` | Singletons allocated at startup — flag block, world state, game data | Return to the main menu |
| `World` | Anything reached through a character instance | Map change, warp, quitout, death |
| `Volatile` | Anything whose resolution *is* the answer, such as the follow camera | Never cached |

`Lifetime` is ordered longest-lived first, and `GameMemory.invalidate(event)` drops every entry
whose tier is at or below `event`. So `invalidate(World)` clears world entries and keeps session
ones; `invalidate(Session)` clears both.

### Displacement: the one step that is not a dereference

Some structures are reached by arithmetic rather than by a pointer. The animation request slots sit
at fixed distances inside one pooled region and **nothing anywhere holds their addresses** — the
game computes them. A `PointerChain` dereferences every offset, so it cannot express that, and a
chain written as though a trailing offset would do it reads whatever the pool stores at that
distance and resolves to an address that looks perfectly ordinary.

`GamePointer.displacement` is added after the walk, with no read. The rules:

1. **Use it only when nothing points at the structure.** If a field holds the address, that field
   is an offset and belongs in the chain. Displacement is for the case where no such field exists.
2. **A failed walk is never displaced.** `Address.Null + 0x1210` is `0x1210` — not null, just a
   low address, so every `isNull` check downstream would wave it through. `GameMemory` checks
   before adding, and `GameMemoryTest` pins it.
3. **Address the structure, then the field — never one combined number.** Our player's channel B
   id happens to land at `pool + 0x1354`, and writing it that way is wrong even though it reads
   correctly: `0x1354` runs off the end of one allocation into another. As
   `channel B + AnimationId` it is a displacement of `0x1210` and a field at `0x144`, each of which
   means something.
4. **Nothing else may displace.** Every other walk ends on a dereference, and a stray displacement
   there would read a fixed distance past the structure it named. `PtdeTablesTest` asserts that the
   only displacing pointers are the two channels.
### Pool adjacency: why a resolved address is not evidence of containment

The sub-objects hanging off a character — the controller, the animation data, the request slots —
are **separate allocations that one pool hands out in sequence**. The `ChrIns` destructor frees
each one individually, which is what proves it; and their being adjacent is what makes it
deceptive. Two consequences:

- **A pointer resolving inside another structure's byte range does not mean it is embedded.** Our
  controller pointer lands a few hundred bytes into the character's own span, and reading that as
  containment is what produced a 0x1354 "field" that was really another object's `0x144`.
- **`whereIs`-style arithmetic across these is unsound.** An address a little past the end of one
  structure is at least as likely to be the start of the next as a field we have not named.