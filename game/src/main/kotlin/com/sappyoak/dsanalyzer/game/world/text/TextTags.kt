package com.sappyoak.dsanalyzer.game.world.text

import com.sappyoak.dsanalyzer.formats.fmg.Fmg

/** `<?name?>` or `<?name@id?>`, as the game's text embeds what it fills in when it shows a line */
private val TAG = Regex("""<\?(\w+)(?:@(\d+))?\?>""")
private val WORD_BREAK = Regex("(?<=[a-z])(?=[A-Z])")

private const val GOODS = "gdsparam"
private const val SYSTEM = "sysmsg"

/**
 * Fills in the tags the game's text carries. A goods or system message tag reads the text it names;
 * the rest are filled at run time, a player's name or a price, and become a bracketed stand-in
 */
public class TextTags(private val goods: (Int) -> String?, private val system: Fmg?) {
    public fun resolve(text: String): String = TAG.replace(text) { match ->
        val (tag, id) = match.destructured
        val named = id.toIntOrNull()?.let { number ->
            when (tag) {
                GOODS -> goods(number)
                SYSTEM -> system?.get(number)
                else -> null
            }
        }
        named ?: "[${tag.split(WORD_BREAK).joinToString(" ") { it.lowercase() }}]"
    }
}
