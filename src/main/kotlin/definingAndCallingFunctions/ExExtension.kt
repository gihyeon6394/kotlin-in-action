package definingAndCallingFunctions

fun String.lastChar(): Char = this.get(this.length - 1)

fun main() {
    println("Kotlin".lastChar())

    var aespa = listOf("Karina", "Giselle", "Winter", "Ningning")
    println(aespa.joinToString())

    println("Aespa".lastChar)

}

fun <T> Collection<T>.joinToString(separator: String = ", ", prefix: String = "", postfix: String = ""): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

val String.lastChar: Char get() = get(length - 1)
