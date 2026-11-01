package com.sappyoak.dsanalyzer.game.ref

import com.sappyoak.dsanalyzer.game.GameEdition

internal const val CODE_SCHEME = "code:"


/**
 * Something in a game binary that a finding can cite such as a function, a class, a global,
 * a struct field, or a bare address
 */
public data class CodeRef(
    public val binary: BinaryVersion,
    public val target: CodeTarget
) : Address {
    override fun toAddress(): String = "$CODE_SCHEME${binary.token}/${target.path}"
}

public data class BinaryVersion(
    public val edition: GameEdition,
    public val version: String
) {
    init {
        require(version.isNotEmpty()) { "A binary citation needs a version" }
        require(version.none { it == '/' || it == '@' }) {
            "A version cannot contain '/' or '@' which separate an address's parts: '$version'"
        }
    }
}

private val BinaryVersion.token: String get() = "${edition.token}@$version"

internal val GameEdition.token: String
    get() = when (this) {
        GameEdition.PrepareToDie -> "ptde"
        GameEdition.Remastered -> "dsr"
    }

private val CodeTarget.path: String
    get() = when (this) {
        is CodeTarget.Function -> "fn/$name${at.suffix}"
        is CodeTarget.Class -> "class/$name"
        is CodeTarget.Global -> "global/$name${at.suffix}"
        is CodeTarget.Field -> "struct/$struct+${offset.toLong().hex}"
        is CodeTarget.Location -> at.hex
    }

private val Long?.suffix: String get() = this?.let { "@${it.hex}" } ?: ""


public sealed interface CodeTarget {
    /** A named function, optionally with where it sat when the citation was written */
    public data class Function(
        public val name: String,
        public val at: Long? = null
    ) : CodeTarget {
        init {
            requireSymbol(name, "function")
            requireAddress(at)
        }
    }

    /**
     * A class, by the name its RTTI carries
     */
    public data class Class(public val name: String) : CodeTarget {
        init {
            requireSymbol(name, "class")
        }
    }

    public data class Global(
        public val name: String,
        public val at: Long? = null
    ) : CodeTarget {
        init {
            requireSymbol(name, "global")
            requireAddress(at)
        }
    }

    /**
     * A field of a structure the binary defines, by offset
     *
     * By offset and not by name on purpose. Unlike a Havok member or a param column, there is no table in
     * the binary naming these, so the offset is the only thing the *game* states.
     */
    public data class Field(
        public val struct: String,
        public val offset: Int
    ) : CodeTarget {
        init {
            requireSymbol(struct, "struct")
            require(offset >= 0) { "A field offset cannot be negative: $offset" }
        }
    }

    /** A bare address */
    public data class Location(public val at: Long) : CodeTarget {
        init {
            require(at >= 0) { "An address cannot be negative: $at" }
        }
    }
}
