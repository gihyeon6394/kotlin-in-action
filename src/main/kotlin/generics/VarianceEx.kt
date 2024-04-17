package generics

fun printContents(list: List<Any>) {
    println(list.joinToString())
}

fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}

fun main() {
    val strings = listOf("abc", "bac")
    printContents(strings) // abc, bac

    val strs = mutableListOf("abc", "bac")
    // addAnswer(strs) // compile err : Type mismatch: inferred type is MutableList<String> but MutableList<Any> was expected

    val s: String = "abc"
    val t: String? = s

    // val ss: String = t // compile err : Type mismatch: inferred type is String? but String was expected

}
