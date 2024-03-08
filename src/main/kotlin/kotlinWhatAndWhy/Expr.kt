package kotlinWhatAndWhy

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun main() {
    // 1 + (2 + 3)
    val sum = Sum(
        Num(1), Sum(
            Num(2), Num(3)
        )
    )

    println(eval(sum))
}

fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }

    if (e is Sum) {
        return eval(e.left) + eval(e.right)
    }

    throw IllegalArgumentException("Unknown expression")
}

fun eval2(e: Expr): Int = if (e is Num) e.value
else if (e is Sum) eval2(e.left) + eval2(e.right)
else throw IllegalArgumentException("Unknown expression")

fun eval3(e: Expr): Int = when (e) {
    is Num -> e.value
    is Sum -> eval3(e.left) + eval3(e.right)
    else -> throw IllegalArgumentException("Unknown expression")
}

fun evalWithLogging(e: Expr): Int = when (e) {
    is Num -> {
        println("num: ${e.value}")
        e.value
    }

    is Sum -> {
        val left = evalWithLogging(e.left)
        val right = evalWithLogging(e.right)
        println("sum: $left + $right")
        left + right
    }

    else -> throw IllegalArgumentException("Unknown expression")
}