package programmingWithLamdas

data class Singer(val name: String, val nameGroup: String)

fun main() {
    val singers = listOf(
        Singer("Karina", "aespa"),
        Singer("Giselle", "aespa"),
        Singer("Ningning", "aespa"),
        Singer("Jennie", "BLACKPINK"),
        Singer("Minzi", "NewJeans")
    )

    println(getSingersName(singers))
    println(getSingersNameWith(singers))
    println(getSingersNameWithBetter(singers))
    println(getSingersNameWithApply(singers))
    println(getSingersSimpleName(singers))

}

fun getSingersName(people: List<Singer>): String {
    val names = StringBuilder()
    people.forEach {
        names.append(it.name).append(", ")
    }
    return names.toString()
}


// with (Kotlin standard library function)
fun getSingersNameWith(people: List<Singer>): String {
    val names = StringBuilder()
    return with(names) {
        people.forEach {
            append(it.name).append(", ")
        }
        toString()
    }
}

fun getSingersNameWithBetter(people: List<Singer>): String {
    return with(StringBuilder()) {
        people.forEach {
            append(it.name).append(", ")
        }
        toString()
    }
}

fun getSingersNameWithApply(people: List<Singer>): String {
    return StringBuilder().apply {
        people.forEach {
            append(it.name).append(", ")
        }
    }.toString()
}

fun getSingersSimpleName(people: List<Singer>): String {
    return people.joinToString { it.name }
}