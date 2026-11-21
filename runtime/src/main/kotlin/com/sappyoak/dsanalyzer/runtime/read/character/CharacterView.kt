package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.CharacterLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * One character, the ChrIns and what it points to. [refresh] reads the ChrIns;
 * [control], [pose] and [animData] are followed only when asked, and at most once per refresh,
 * so every reader that needs them shares the one read.
 *
 * ChrIns -> ChrCtrl -> ChrPosData (posed position, angle)
 *                   -> AnimData   (play speed)
 * ChrIns -> ComManipulator        (the target, on editions that keep it there)
 */
internal class CharacterView(private val layout: CharacterLayout) {
    private val chr = layout.chr

    /** The ChrIns fields, for readers whose offsets live in their own layouts */
    val fields = StructView(chr.size)

    private val controlHop = Hop(StructView(layout.control.size))
    private val poseHop = Hop(StructView(layout.pose.size))
    private val animHop = Hop(StructView(layout.control.playSpeed + Float.SIZE_BYTES))
    private val word = StructView(Int.SIZE_BYTES)

    private var memory: ProcessMemory? = null

    val address: Address get() = fields.address
    val handle: Int get() = fields.int(chr.handle)
    val modelId: Int get() = fields.int(chr.modelId)

    /** The ChrCtrl's address, which may be null for a character still loading */
    val controller: Address get() = fields.pointer(chr.control)
    val manipulator: Address get() = fields.pointer(chr.comManipulator)
    val effects: Address get() = fields.pointer(chr.specialEffect)

    fun refresh(memory: ProcessMemory, at: Address): Boolean {
        this.memory = memory
        listOf(controlHop, poseHop, animHop).forEach(Hop::reset)
        return fields.refresh(memory, at)
    }

    /** The ChrCtrl, or null when the character has none yet */
    fun control(): StructView? = controlHop.follow { controller }

    fun pose(): StructView? = control()?.let { ctrl -> poseHop.follow { ctrl.pointer(layout.control.position) } }

    fun animData(): StructView? = control()?.let { ctrl -> animHop.follow { ctrl.pointer(layout.control.animData) } }

    /** Where the character stands: its posed position when [posed] and present, else its transform's */
    fun position(posed: Boolean): Vec3 = (if (posed) pose()?.vec3(layout.pose.position) else null) ?: transform()

    /** The handle it targets, read wherever this edition keeps it */
    fun target(): Int? = when (val target = chr.target) {
        is TargetLayout.InCharacter -> fields.int(target.offset)
        is TargetLayout.InManipulator -> {
            val at = manipulator
            if (!at.isNull && word.refresh(checkNotNull(memory), at + target.offset.toLong())) word.int(0) else null
        }
    }

    fun ref(): CharacterRef = CharacterRef(handle, address, manipulator, controller)

    private fun transform(): Vec3 {
        val at = chr.position
        return Vec3(fields.float(at.x), fields.float(at.x + at.stride), fields.float(at.x + 2 * at.stride))
    }

    /** One pointer followed lazily, remembered until the next [reset] */
    private inner class Hop(private val view: StructView) {
        private var read: Boolean? = null

        fun reset() {
            read = null
        }

        fun follow(at: () -> Address): StructView? {
            val present = read ?: view.refresh(checkNotNull(memory), at()).also { read = it }
            return view.takeIf { present }
        }
    }
}
