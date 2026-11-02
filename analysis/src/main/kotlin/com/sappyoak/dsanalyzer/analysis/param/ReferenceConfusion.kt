package com.sappyoak.dsanalyzer.analysis.param

import com.sappyoak.dsanalyzer.analysis.Citation
import com.sappyoak.dsanalyzer.analysis.engine.AnalysisContext
import com.sappyoak.dsanalyzer.analysis.engine.AnalysisResult
import com.sappyoak.dsanalyzer.analysis.engine.Analyzer
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerCost
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerDescriptor
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerId
import com.sappyoak.dsanalyzer.analysis.engine.BugPattern
import com.sappyoak.dsanalyzer.analysis.engine.Confidence
import com.sappyoak.dsanalyzer.analysis.engine.ContextKey
import com.sappyoak.dsanalyzer.analysis.engine.Coverage
import com.sappyoak.dsanalyzer.analysis.engine.Gap
import com.sappyoak.dsanalyzer.analysis.engine.Lead
import com.sappyoak.dsanalyzer.analysis.engine.LeadKey
import com.sappyoak.dsanalyzer.analysis.engine.LeadKind
import com.sappyoak.dsanalyzer.analysis.engine.resultOf
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.refs.ParamRef
import com.sappyoak.dsanalyzer.formats.param.refs.references
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.ref.toDataAddress
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.paramFor
import com.sappyoak.dsanalyzer.game.world.params.side

private val ID = AnalyzerId("param.reference-confusion")

private val DANGLING = LeadKind("dangling-reference", "Reference to a missing row", weight = 8)
private val CROSS_TABLE = LeadKind("cross-table-reference", "Missing row another table has", weight = 9)

/** The tables an item id can be read through; an id missing from one may be real in another */
private val EQUIPMENT = listOf(
    GameParam.EquipParamWeapon,
    GameParam.EquipParamProtector,
    GameParam.EquipParamAccessory,
    GameParam.EquipParamGoods
)

public object ReferenceConfusion : Analyzer<GameParams> {
    override val input: ContextKey<GameParams> get() = GameParamsInput

    override val descriptor: AnalyzerDescriptor = AnalyzerDescriptor(
        id = ID,
        version = 1,
        displayName = "Dangling and cross-table references",
        summary = "Param references naming a row their table does not have",
        mechanism = "A slot names a row by id in the table its type field picks. Where that table has no " +
                "such row the id resolves to nothing; where another equipment table has it, the same id is a " +
                "real but different item read through the wrong table",
        kinds = listOf(DANGLING, CROSS_TABLE),
        patterns = setOf(BugPattern.TypeConfusion),
        cost = AnalyzerCost.Cheap
    )

    override suspend fun analyze(context: AnalysisContext<GameParams>): AnalysisResult {
        val params = context.data
        val leads = ArrayList<Lead>()
        var examined = 0
        var unresolvable = 0

        for ((param, table) in params.tables) {
            for (row in table.rows) {
                for ((field, ref) in row.references) {
                    if (ref.id == 0) continue
                    examined++
                    val target = ref.kind.paramFor(param.side)
                    val targetTable = params.tables[target]
                    when {
                        targetTable == null -> unresolvable++
                        targetTable[ref.id] == null -> leads += leadFor(params, param, row, field, ref, target)
                    }
                }
            }
        }

        val gaps = listOfNotNull(
            Gap(unresolvable, "name a table this edition does not have").takeIf { unresolvable > 0 }
        )
        return resultOf(leads.distinctBy { it.key }, Coverage(examined, "references", gaps))
    }

    private fun leadFor(
        params: GameParams,
        source: GameParam<*>,
        row: ParamRow,
        field: String,
        ref: ParamRef,
        target: GameParam<*>
    ): Lead {
        val elsewhere = EQUIPMENT.filter { it != target && params.tables[it]?.get(ref.id) != null }
        val crossTable = elsewhere.isNotEmpty()

        return Lead(
            key = LeadKey.of(ID, "${source.fileName}/${row.id}/$field"),
            kind = if (crossTable) CROSS_TABLE else DANGLING,
            title = "${source.fileName} ${row.id} names ${target.fileName} ${ref.id}, which does not exist",
            detail = buildString {
                append("$field of ${source.fileName} row ${row.id} is ${ref.id}, read as a ${ref.kind} through ")
                append("${target.fileName}, which has no such row. ")
                if (crossTable) {
                    append("${elsewhere.joinToString { it.fileName }} does, so the same id is a real item ")
                    append("in another table: what the game makes of it depends on which table it is read through.")
                } else {
                    append("No equipment table has it either, so the game resolves it to nothing.")
                }
            },
            confidence = if (crossTable) Confidence.High else Confidence.Medium,
            citations = listOf(
                cite(source, row.id, "$field = ${ref.id}"),
                cite(target, ref.id, "missing")
            ) + elsewhere.map { cite(it, ref.id, "exists here") },
            patterns = setOf(BugPattern.TypeConfusion),
            precedent = "Equip Slot Manipulation".takeIf { crossTable }
        )
    }

    private fun cite(param: GameParam<*>, id: Int, label: String): Citation =
        Citation.of(WorldRef.ParamRow(param.fileName, id).toDataAddress(), "${param.fileName} $id · $label")
}
