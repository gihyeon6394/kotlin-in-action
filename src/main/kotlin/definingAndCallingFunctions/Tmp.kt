package definingAndCallingFunctions

fun main() {
    val aespa = listOf("Karina", "Winter", "NingNing", "Giselle")
    println(aespa.joinToString( "; ", "(", ")"))
    println(aespa.joinToString(separator = "; ", prefix = "(", postfix = ")"))
    println(aespa.joinToString())
    println(aespa.joinToString(", "))

}