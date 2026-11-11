package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.inject.Mem
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.native.memory.intAt

/**
 * The block of game memory the subs and the tool share
 *
 * Every field is a little-endian dword with **one writer**, either a stub or the tool,
 * so neither side ever does a read-modify-write the other could interleave with.
 * Fresh allocations are zeroed, which is every field's starting state
 *
 * After the fields come to the two trigger banks, the pad ring ([RING_CAPACITY] records of
 * [RECORD_SIZE] bytes, each belonging to whichever side the pad cursor and limit hand it to, see
 * [PadOverride]) and the gate's RNG log
 */
public class Mailbox private constructor(
    private val memory: ProcessMemory,
    private val writer: GameWriter,
    public val base: Address
) {
    /** The operand a stub addresses [field] with */
    internal fun operand(field: Field): Mem = Mem.Absolute(base + field.offset.toLong())

    internal fun read(field: Field): Int? =
        memory.intAt(base + field.offset.toLong())

    internal fun write(field: Field, value: Int): Boolean {
        check(field.writer == Writer.Tool) { "$field belongs to the stubs" }
        return writer.fillDword(base + field.offset.toLong(), value)
    }

    /** Where ring record [index] lives; indices wrap */
    internal fun record(index: Int): Address = ring + ((index and RING_MASK) * RECORD_SIZE).toLong()

    internal fun readRecord(index: Int): ByteArray? = memory.read(record(index), RECORD_SIZE)

    internal fun writeRecord(index: Int, bytes: ByteArray): Boolean {
        require(bytes.size == RECORD_SIZE) { "A record is $RECORD_SIZE bytes" }
        return writer.fill(record(index), bytes)
    }

    internal val ring: Address get() = base + RING_OFFSET.toLong()

    /** The gate's per-frame RNG log, [LOG_ENTRIES] entries of [LOG_ENTRY_SIZE] bytes; the stub's alone */
    internal val log: Address get() = base + LOG_OFFSET.toLong()

    /** The log entry frame [frame] was written to, if it is still there */
    internal fun readLog(frame: Int): ByteArray? =
        memory.read(log + ((frame and LOG_MASK) * LOG_ENTRY_SIZE).toLong(), LOG_ENTRY_SIZE)

    internal fun bank(bank: Int): Address = base + (BANKS_OFFSET + (bank and 1) * BANK_SIZE).toLong()
    internal fun write(bank: Int, slot: Slot, value: Int): Boolean =
        writer.fillDword(bank(bank) + slot.offset.toLong(), value)

    internal enum class Writer { Stub, Tool }

    internal enum class Field(val offset: Int, val writer: Writer) {
        /** Frames the gate has let through since it was installed */
        Frame(0x00, Writer.Stub),

        /** Non-zero holds the game at the gate */
        Paused(0x04, Writer.Tool),

        /** Frames granted while paused; the gate runs one each time it falls behind this */
        StepsGranted(0x08, Writer.Tool),

        /** Frames the gate has run against [StepsGranted] */
        StepsTaken(0x0C, Writer.Stub),

        /** [PadMode] as an int; off makes the pad stub do nothing */
        PadMode(0x10, Writer.Tool),

        /** Bumped by the tool to start a fresh run of the ring from record 0 */
        PadEpoch(0x14, Writer.Tool),

        /** The cursor may not reach this: records below it are the stub's to use */
        PadLimit(0x18, Writer.Tool),

        /** The epoch the stub last reset its cursor for */
        PadSeen(0x1C, Writer.Stub),

        /** Records the stub has played or recorded this epoch */
        PadCursor(0x20, Writer.Stub),

        /** The player's `PadDevice`; every wrapper also polls a keyboard and a mouse through the same site */
        PadDevice(0x24, Writer.Tool),

        /**
         * Bumped to arm the gate's trigger; it fires once per arm. Its low bit picks the condition
         * bank in use, so the tool writes the other bank and switches with this one write
         */
        TriggerArm(0x28, Writer.Tool),

        /** The arm the trigger last fired for; the gate holds while this differs from [TriggerRelease] */
        TriggerFired(0x2C, Writer.Stub),

        /** Set to [TriggerFired] to let a triggered hold go */
        TriggerRelease(0x30, Writer.Tool),

        /** Whether the condition held last frame: the trigger fires on it becoming true */
        TriggerLast(0x34, Writer.Stub),

        /** The arm the stub has seeded [TriggerLast] for: a fresh arm never fires on its first frame */
        TriggerSeen(0x38, Writer.Stub),

        /** Non-zero holds each frame until every requested file read has completed (plan 32) */
        LoadBarrier(0x3C, Writer.Tool)
    }

    /** A dword in a trigger condition bank, which belongs to the tool */
    internal enum class Slot(val offset: Int) {
        /** The watched value is this address walked through [Depth] offsets, as a pointer chain */
        Base(0x00),
        Depth(0x04),
        Offset0(0x08),
        Offset1(0x0C),
        Offset2(0x10),
        Offset3(0x14),

        /** The condition is `(value & Mask) == Value`, inverted when [Negate] is set */
        Mask(0x18),
        Value(0x1C),
        Negate(0x20);

        companion object {
            val Offsets: List<Slot> = listOf(Offset0, Offset1, Offset2, Offset3)
        }
    }

    public companion object {
        /** A power of two, so a record's slot is its index masked: 34 s of 30 Hz input */
        internal const val RING_CAPACITY = 1024
        internal const val RING_MASK = RING_CAPACITY - 1
        internal const val RECORD_SIZE = 32
        internal const val RECORD_SHIFT = 5
        internal const val BANKS_OFFSET = 0x40
        internal const val BANK_SIZE = 0x40
        internal const val BANK_SHIFT = 6
        private const val RING_OFFSET = 0xC0
        internal const val LOG_ENTRIES = 256
        internal const val LOG_MASK = LOG_ENTRIES - 1
        internal const val LOG_ENTRY_SIZE = 16
        internal const val LOG_SHIFT = 4
        private const val LOG_OFFSET = RING_OFFSET + RING_CAPACITY * RECORD_SIZE
        private const val SIZE = LOG_OFFSET + LOG_ENTRIES * LOG_ENTRY_SIZE

        public fun allocate(memory: ProcessMemory, writer: GameWriter): Mailbox? =
            writer.allocate(SIZE)?.let { Mailbox(memory, writer, it) }
    }
}
