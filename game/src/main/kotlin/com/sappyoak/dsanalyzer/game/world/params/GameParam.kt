// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: GameParam.parambnd, read from the installation

package com.sappyoak.dsanalyzer.game.world.params

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.formats.param.defs.AiStandardInfoRow
import com.sappyoak.dsanalyzer.formats.param.defs.AtkParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.BehaviorParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.BulletParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.CalcCorrectGraphRow
import com.sappyoak.dsanalyzer.formats.param.defs.CharaInitParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.CoolTimeParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.EnemyStandardInfoRow
import com.sappyoak.dsanalyzer.formats.param.defs.EquipMtrlSetParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.EquipParamAccessoryRow
import com.sappyoak.dsanalyzer.formats.param.defs.EquipParamGoodsRow
import com.sappyoak.dsanalyzer.formats.param.defs.EquipParamProtectorRow
import com.sappyoak.dsanalyzer.formats.param.defs.EquipParamWeaponRow
import com.sappyoak.dsanalyzer.formats.param.defs.FaceGenParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.GameAreaParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.HitMtrlParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ItemLotParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.KnockBackParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.LevelSyncParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.LockCamParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.MagicParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.MenuParamColorTableRow
import com.sappyoak.dsanalyzer.formats.param.defs.MoveParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.NpcParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.NpcThinkParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ObjActParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ObjectParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.QwcChangeParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.QwcJudgeParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.RagdollParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ReinforceParamProtectorRow
import com.sappyoak.dsanalyzer.formats.param.defs.ReinforceParamWeaponRow
import com.sappyoak.dsanalyzer.formats.param.defs.ShopLineupParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.SkeletonParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.SpEffectRow
import com.sappyoak.dsanalyzer.formats.param.defs.SpEffectVfxRow
import com.sappyoak.dsanalyzer.formats.param.defs.TalkParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ThrowParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.WhiteCoolTimeParamRow

/**
 * Every file 'GameParam.parambnd' holds, with the row type each edition reads it through
 *
 * A null slot means that edition's install has no such param, so nothing can read it there
 */
public sealed class GameParam<R : ParamRow>(
    public val fileName: String,
    public val prepareToDie: ParamRowType<R>?,
    public val remastered: ParamRowType<R>?
) {
    public data object AIStandardInfoBank : GameParam<AiStandardInfoRow>("default_AIStandardInfoBank", prepareToDie = AiStandardInfoRow, remastered = AiStandardInfoRow)
    public data object AtkParamNpc : GameParam<AtkParamRow>("AtkParam_Npc", prepareToDie = AtkParamRow, remastered = AtkParamRow)
    public data object AtkParamPc : GameParam<AtkParamRow>("AtkParam_Pc", prepareToDie = AtkParamRow, remastered = AtkParamRow)
    public data object BehaviorParam : GameParam<BehaviorParamRow>("BehaviorParam", prepareToDie = BehaviorParamRow, remastered = BehaviorParamRow)
    public data object BehaviorParamPc : GameParam<BehaviorParamRow>("BehaviorParam_PC", prepareToDie = BehaviorParamRow, remastered = BehaviorParamRow)
    public data object Bullet : GameParam<BulletParamRow>("Bullet", prepareToDie = BulletParamRow, remastered = BulletParamRow)
    public data object CalcCorrectGraph : GameParam<CalcCorrectGraphRow>("CalcCorrectGraph", prepareToDie = CalcCorrectGraphRow, remastered = CalcCorrectGraphRow)
    public data object CharaInitParam : GameParam<CharaInitParamRow>("CharaInitParam", prepareToDie = CharaInitParamRow, remastered = CharaInitParamRow)
    public data object CoolTimeParam : GameParam<CoolTimeParamRow>("CoolTimeParam", prepareToDie = null, remastered = CoolTimeParamRow)
    public data object EnemyBehaviorBank : GameParam<EnemyStandardInfoRow>("default_EnemyBehaviorBank", prepareToDie = EnemyStandardInfoRow, remastered = EnemyStandardInfoRow)
    public data object EquipMtrlSetParam : GameParam<EquipMtrlSetParamRow>("EquipMtrlSetParam", prepareToDie = EquipMtrlSetParamRow, remastered = EquipMtrlSetParamRow)
    public data object EquipParamAccessory : GameParam<EquipParamAccessoryRow>("EquipParamAccessory", prepareToDie = EquipParamAccessoryRow, remastered = EquipParamAccessoryRow)
    public data object EquipParamGoods : GameParam<EquipParamGoodsRow>("EquipParamGoods", prepareToDie = EquipParamGoodsRow.PrepareToDie, remastered = EquipParamGoodsRow.Remastered)
    public data object EquipParamProtector : GameParam<EquipParamProtectorRow>("EquipParamProtector", prepareToDie = EquipParamProtectorRow, remastered = EquipParamProtectorRow)
    public data object EquipParamWeapon : GameParam<EquipParamWeaponRow>("EquipParamWeapon", prepareToDie = EquipParamWeaponRow.PrepareToDie, remastered = EquipParamWeaponRow.Remastered)
    public data object FaceGenParam : GameParam<FaceGenParamRow>("FaceGenParam", prepareToDie = FaceGenParamRow, remastered = FaceGenParamRow)
    public data object GameAreaParam : GameParam<GameAreaParamRow>("GameAreaParam", prepareToDie = GameAreaParamRow, remastered = GameAreaParamRow)
    public data object HitMtrlParam : GameParam<HitMtrlParamRow>("HitMtrlParam", prepareToDie = HitMtrlParamRow, remastered = HitMtrlParamRow)
    public data object ItemLotParam : GameParam<ItemLotParamRow>("ItemLotParam", prepareToDie = ItemLotParamRow, remastered = ItemLotParamRow)
    public data object KnockBackParam : GameParam<KnockBackParamRow>("KnockBackParam", prepareToDie = KnockBackParamRow, remastered = KnockBackParamRow)
    public data object LevelSyncParam : GameParam<LevelSyncParamRow>("LevelSyncParam", prepareToDie = null, remastered = LevelSyncParamRow)
    public data object LockCamParam : GameParam<LockCamParamRow>("LockCamParam", prepareToDie = LockCamParamRow, remastered = LockCamParamRow)
    public data object Magic : GameParam<MagicParamRow>("Magic", prepareToDie = MagicParamRow, remastered = MagicParamRow)
    public data object MenuColorTableParam : GameParam<MenuParamColorTableRow>("MenuColorTableParam", prepareToDie = MenuParamColorTableRow, remastered = MenuParamColorTableRow)
    public data object MoveParam : GameParam<MoveParamRow>("MoveParam", prepareToDie = MoveParamRow, remastered = MoveParamRow)
    public data object NpcParam : GameParam<NpcParamRow>("NpcParam", prepareToDie = NpcParamRow, remastered = NpcParamRow)
    public data object NpcThinkParam : GameParam<NpcThinkParamRow>("NpcThinkParam", prepareToDie = NpcThinkParamRow, remastered = NpcThinkParamRow)
    public data object ObjActParam : GameParam<ObjActParamRow>("ObjActParam", prepareToDie = ObjActParamRow, remastered = ObjActParamRow)
    public data object ObjectParam : GameParam<ObjectParamRow>("ObjectParam", prepareToDie = ObjectParamRow, remastered = ObjectParamRow)
    public data object QwcChange : GameParam<QwcChangeParamRow>("QwcChange", prepareToDie = QwcChangeParamRow, remastered = QwcChangeParamRow)
    public data object QwcJudge : GameParam<QwcJudgeParamRow>("QwcJudge", prepareToDie = QwcJudgeParamRow, remastered = QwcJudgeParamRow)
    public data object RagdollParam : GameParam<RagdollParamRow>("RagdollParam", prepareToDie = RagdollParamRow, remastered = RagdollParamRow)
    public data object ReinforceParamProtector : GameParam<ReinforceParamProtectorRow>("ReinforceParamProtector", prepareToDie = ReinforceParamProtectorRow, remastered = ReinforceParamProtectorRow)
    public data object ReinforceParamWeapon : GameParam<ReinforceParamWeaponRow>("ReinforceParamWeapon", prepareToDie = ReinforceParamWeaponRow.PrepareToDie, remastered = ReinforceParamWeaponRow.Remastered)
    public data object ShopLineupParam : GameParam<ShopLineupParamRow>("ShopLineupParam", prepareToDie = ShopLineupParamRow, remastered = ShopLineupParamRow)
    public data object SkeletonParam : GameParam<SkeletonParamRow>("SkeletonParam", prepareToDie = SkeletonParamRow, remastered = SkeletonParamRow)
    public data object SpEffectParam : GameParam<SpEffectRow>("SpEffectParam", prepareToDie = SpEffectRow, remastered = SpEffectRow)
    public data object SpEffectVfxParam : GameParam<SpEffectVfxRow>("SpEffectVfxParam", prepareToDie = SpEffectVfxRow, remastered = SpEffectVfxRow)
    public data object TalkParam : GameParam<TalkParamRow>("TalkParam", prepareToDie = TalkParamRow, remastered = TalkParamRow)
    public data object ThrowParam : GameParam<ThrowParamRow>("ThrowParam", prepareToDie = ThrowParamRow, remastered = ThrowParamRow)
    public data object WhiteCoolTimeParam : GameParam<WhiteCoolTimeParamRow>("WhiteCoolTimeParam", prepareToDie = null, remastered = WhiteCoolTimeParamRow)

    /** The row type [edition] reads this param through, or null where it has no such param */
    public fun typeFor(edition: GameEdition): ParamRowType<R>? = when (edition) {
        GameEdition.PrepareToDie -> prepareToDie
        GameEdition.Remastered -> remastered
    }

    public companion object {
        public val entries: List<GameParam<*>> by lazy {
            listOf(
                AIStandardInfoBank,
                AtkParamNpc,
                AtkParamPc,
                BehaviorParam,
                BehaviorParamPc,
                Bullet,
                CalcCorrectGraph,
                CharaInitParam,
                CoolTimeParam,
                EnemyBehaviorBank,
                EquipMtrlSetParam,
                EquipParamAccessory,
                EquipParamGoods,
                EquipParamProtector,
                EquipParamWeapon,
                FaceGenParam,
                GameAreaParam,
                HitMtrlParam,
                ItemLotParam,
                KnockBackParam,
                LevelSyncParam,
                LockCamParam,
                Magic,
                MenuColorTableParam,
                MoveParam,
                NpcParam,
                NpcThinkParam,
                ObjActParam,
                ObjectParam,
                QwcChange,
                QwcJudge,
                RagdollParam,
                ReinforceParamProtector,
                ReinforceParamWeapon,
                ShopLineupParam,
                SkeletonParam,
                SpEffectParam,
                SpEffectVfxParam,
                TalkParam,
                ThrowParam,
                WhiteCoolTimeParam
            )
        }
    }
}
