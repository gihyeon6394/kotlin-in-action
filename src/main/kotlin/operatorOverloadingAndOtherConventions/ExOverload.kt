package operatorOverloadingAndOtherConventions

import java.math.BigDecimal

fun main() {
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)

    val p3 = Point(10, 20)
    println(p3 * 1.5)

    val p4 = Point(10, 20)
    println(1.5 * p4)

    var p6 = Point(1, 2)
    p6 += Point(3, 4)
    println(p6) // Point(x=4, y=6)

    var l1 = ArrayList<Int>()
    l1 += 42
    println(l1) // [42]\

    val l2 = arrayListOf(1, 2)
    l2 += 3

    val newList = l2 + listOf(4, 5)

    val p7 = Point(10, 20)
    println(-p7) // Point(x=-10, y=-20)

    var bd = BigDecimal.ZERO
    println(bd++) // 0
    println(++bd) // 2

    val person1 = Person("Alice", "Smith")
    val person2 = Person("Bob", "Johnson")
    println(person1 < person2) // false

}


data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun times(scale: Double): Point {
        return Point((x * scale).toInt(), (y * scale).toInt())
    }

    operator fun unaryMinus(): Point {
        return Point(-x, -y)
    }
}

operator fun Double.times(p: Point): Point {
    return Point((p.x * this).toInt(), (p.y * this).toInt())
}

operator fun BigDecimal.inc() = this + BigDecimal.ONE


class Person(
    val firstName: String, val lastName: String,
) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return compareValuesBy(
            this, other,
            Person::lastName, Person::firstName
        )
    }
}

data class MutablePoint(var x: Int, var y: Int) {
}

operator fun MutablePoint.set(index: Int, value: Int) {
    return when (index) {
        0 -> x = value
        1 -> y = value
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}
