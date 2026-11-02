package com.sappyoak.dsanalyzer.formats.esd

public class Esd(
    public val name: String?,
    public val groups: Map<Long, StateGroup>
)

public class StateGroup(
    public val id: Long,
    public val states: Map<Long, EsdState>
)

public class EsdState(
    public val id: Long,
    public val conditions: List<EsdCondition>,
    public val entryCommands: List<CommandCall>,
    public val whileCommands: List<CommandCall>,
    public val exitCommands: List<CommandCall>
)

public class EsdCondition(
    public val target: Long?,
    public val evaluator: Bytecode,
    public val passCommands: List<CommandCall>,
    public val subconditions: List<EsdCondition>
)

public class CommandCall(
    public val bank: Int,
    public val id: Int,
    public val args: List<Bytecode>
) {
    override fun toString(): String = "$bank[$id]"
}

public class Bytecode(internal val bytes: ByteArray) {
    public val size: Int get() = bytes.size

    public fun hex(): String = bytes.joinToString(" ") { "%02X".format(it) }
}
