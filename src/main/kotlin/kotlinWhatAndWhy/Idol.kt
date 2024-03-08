package kotlinWhatAndWhy

enum class Idol(val teamName: String, val memberCount: Int) {
    TWICE("TWICE", 9),
    BLACKPINK("BLACKPINK", 4),
    AESPA("AESPA", 4),
    NEW_JEANS("NEW JEANS", 5),
    IVE("IVE", 8),
    ITZY("ITZY", 5);

    fun teamNameWithMemberCount() = "$teamName has $memberCount members"
}

fun main() {
    println(Idol.BLACKPINK.teamNameWithMemberCount())
    println(Idol.AESPA.teamNameWithMemberCount())
}

fun printLeader(idol: Idol) {
    when (idol) {
        Idol.TWICE -> println("Jihyo")
        Idol.BLACKPINK -> println("Jisoo")
        Idol.AESPA -> println("Karina")
        else -> println("I don't know")
    }
}

fun printFavorite(idol: Idol) {
    when (idol) {
        Idol.AESPA -> "Winter"
        Idol.TWICE, Idol.BLACKPINK -> "I can't choose"
        else -> "I don't know"
    }
}

fun hasFriendship(idol1: Idol, idol2: Idol) =
    when (setOf(idol1, idol2)) {
        setOf(Idol.TWICE, Idol.BLACKPINK) -> true
        setOf(Idol.AESPA, Idol.ITZY) -> true
        setOf(Idol.AESPA, Idol.NEW_JEANS) -> true
        else -> false
    }


fun hasFriendshipWithoutArgs(idol1: Idol, idol2: Idol) =
    when {
        idol1 == Idol.TWICE && idol2 == Idol.BLACKPINK -> true
        idol1 == Idol.AESPA && idol2 == Idol.ITZY -> true
        idol1 == Idol.AESPA && idol2 == Idol.NEW_JEANS -> true
        else -> false
    }