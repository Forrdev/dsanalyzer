package com.sappyoak.dsanalyzer.runtime.inject

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

/**
 * Machine code written by hand with named holes the installer fills: the x64 caves, which [X86]
 * does not assemble. Write the bytes from a disassembler listing and name every operand that depends
 * on where things land
 */
public class CaveTemplate(private val code: ByteArray, private val holes: Map<String, Hole>) {
    public sealed interface Hole {
        public val at: Int
        public val width: Int

        /** A `rel32` counted from the end of its four bytes, as `JMP`, `CALL` and RIP operands are */
        public data class Rel32(override val at: Int) : Hole {
            override val width: Int get() = Int.SIZE_BYTES
        }

        public data class Abs64(override val at: Int) : Hole {
            override val width: Int get() = Long.SIZE_BYTES
        }

        public data class Imm32(override val at: Int) : Hole {
            override val width: Int get() = Int.SIZE_BYTES
        }

        /** A signed byte displacement */
        public data class Disp8(override val at: Int) : Hole {
            override val width: Int get() = Byte.SIZE_BYTES
        }
    }

    init {
        holes.forEach { (name, hole) ->
            require(hole.at >= 0 && hole.at + hole.width <= code.size) { "Hole $name falls outside the template" }
        }
    }

    public val size: Int get() = code.size
    public val names: Set<String> get() = holes.keys

    /** The cave as it must read at [origin], every hole filled from [values] */
    public fun build(origin: Address, values: Map<String, Long>): ByteArray {
        require(values.keys == holes.keys) { "Holes ${holes.keys} need values; got ${values.keys}" }
        val out = code.copyOf().littleEndian()
        holes.forEach { (name, hole) ->
            val value = values.getValue(name)
            when (hole) {
                is Hole.Rel32 -> out.putInt(hole.at, int32(name, value - (origin.value + hole.at + Int.SIZE_BYTES)))
                is Hole.Abs64 -> out.putLong(hole.at, value)
                is Hole.Imm32 -> out.putInt(hole.at, int32(name, value))
                is Hole.Disp8 -> {
                    require(value in Byte.MIN_VALUE..Byte.MAX_VALUE) { "$name does not fit in a signed byte: $value" }
                    out.put(hole.at, value.toByte())
                }
            }
        }
        return out.array()
    }

    private fun int32(name: String, value: Long): Int {
        require(value in Int.MIN_VALUE..Int.MAX_VALUE) { "$name does not fit in 32 bits: $value" }
        return value.toInt()
    }

    public companion object {
        /** Bytes from a listing such as `"48 8B 99 70 02 00 00"`; whitespace is ignored */
        public fun hex(listing: String): ByteArray =
            listing.split(Regex("\\s+")).filter { it.isNotEmpty() }.map { it.toInt(16).toByte() }.toByteArray()
    }
}
