package generics

fun printSum(c: Collection<*>) {
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")

    println(intList.sum())
}

fun printSumBetter(c: Collection<Int>) {
    if (c is List<Int>) {
        println(c.sum())
    }
}

fun main() {
    printSum(setOf(1, 2, 3)) // IllegalArgumentException
    printSum(listOf("a", "b", "c")) // ClassCastException

    // printSumBetter(listOf("a", "b", "c"))
}
