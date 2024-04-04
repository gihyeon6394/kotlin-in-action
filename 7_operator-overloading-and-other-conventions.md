# 7. Operator overloading and other conventions

1. Overloading arithmetic operators
2. Overloading comparison operators
3. Conventions used for collections and ranges
4. Destructuring declarations and component functions
5. Reusing property accessor logic: delegated properties
6. Summary

> ### This chapter covers
>
> - Operator overloading
> - Conventions : 다양한 operation을 지원하는 special-named functions
> - Delegated properties

---

- class에 `plus` method를 정의하면 `+` operator를 사용 가능
- _convention_ : `plus` method를 정의하면 `+` operator를 사용 가능

```kotlin
data class Point(val x: Int, val y: Int) 
```

## 1. Overloading arithmetic operators

- primitive type에 대해 arithmetic operators를 사용 가능
- `+` 는 String도 가능

### Overloading binary arithmetic operations

| Expression | Function name |
|------------|---------------|
| a + b      | `plus`        |
| a - b      | `minus`       |
| a * b      | `times`       |
| a / b      | `div`         |
| a % b      | `mod`         |

```kotlin
fun main() {
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)

    val p3 = Point(10, 20)
    println(p3 * 1.5)

    val p4 = Point(10, 20)
    println(1.5 * p4)
}
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun times(scale: Double): Point {
        return Point((x * scale).toInt(), (y * scale).toInt())
    }
}

// extension function
operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

operator fun Double.times(p: Point): Point {
    return Point((p.x * this).toInt(), (p.y * this).toInt())
}

```

### Overloading compound assignment operators

```kotlin
var p6 = Point(1, 2)
p6 += Point(3, 4)
println(p6) // Point(x=4, y=6)

var l1 = ArrayList<Int>()
l1 += 42
println(l1) // [42]

val p7 = Point(10, 20)
p7 += Point(30, 40) // compile error : Val cannot be reassigned

val l2 = arrayListOf(1, 2)
l2 += 3

val newList = l2 + listOf(4, 5)
```

- _compound assignment operators_ : `+=`, `-=`, `*=`, `/=`, `%=` 등
- `+=` : `plusAssign`, `-=` : `minusAssign`, `*=` : `timesAssign`, `/=` : `divAssign`, `%=` : `modAssign`

```kotlin
// kotlin standard library
operator fun <T> MutableCollection<T>.plusAssign(element: T) {
    this.add(element)
}
```

### Overloading unary operators

| Expression | Function name |
|------------|---------------|
| +a         | `unaryPlus`   |
| -a         | `unaryMinus`  |
| !a         | `not`         |
| ++a, a++   | `inc`         |
| --a, a--   | `dec`         |

```kotlin
fun main() {
    val p7 = Point(10, 20)
    println(-p7) // Point(x=-10, y=-20)

    var bd = BigDecimal.ZERO
    println(bd++) // 0
    println(++bd) // 2
}
data class Point(val x: Int, val y: Int) {
    //...
    operator fun unaryMinus(): Point {
        return Point(-x, -y)
    }
}

operator fun BigDecimal.inc() = this + BigDecimal.ONE
```

## 2. Overloading comparison operators

- comparison operators : `==`, `!=`, `<`, `>`, `<=`, `>=`

### Equality operators: `equals`

- kotlin의 `==`, `!=`는 `equals`를 호출

```kotlin
class Point(val x: Int, val y: Int) {
    override fun equals(obj: Any?): Boolean {
        if (obj === this) return true
        if (obj !is Point) return false
        return obj.x == x && obj.y == y
    }
}

println(Point(10, 20) == Point(10, 20)) // true
println(Point(10, 20) != Point(5, 5)) // true
println(null == Point(1, 2)) // false
```

- `===` : _identity equals_ operator
    - reference equality를 확인

### Ordering operators: `compareTo`

- Java에서 Object 비교 (정렬, 크기 등)를 위해선 명시적으로 `compareTo`를 구현, 호출해야함
- `compareTo`는 `==`, `<`, `>`, `<=`, `>=`를 사용 가능하게 함
- Kotlin은 `Comparaable` 구현체에 대해 convention을 제공
    - `compareTo`를 구현하면 `==`, `<`, `>`, `<=`, `>=`를 사용 가능

```kotlin
class Person(
    val firstName: String, val lastName: String
) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return compareValuesBy(
            this, other,
            Person::lastName, Person::firstName
        )
    }
}

fun main() {
    val person1 = Person("Alice", "Smith")
    val person2 = Person("Bob", "Johnson")
    println(person1 < person2) // false
}
```

- `compareValuesBy` : `compareTo`를 구현하는데 도움을 주는 함수
    - 여러 field를 순차적으로 비교

## 3. Conventions used for collections and ranges

- _index operator_ : `[]`
- `in` operator : contains, range, iteration

### Accessing elements by index: `get` and `set`

```kotlin

fun main() {
    val p = Point(10, 20)
    println(p[1]) // 20

    val mp = MutablePoint(10, 20)
    mp[1] = 42
    println(mp) // MutablePoint(x=10, y=42)
}

operator fun Point.get(index: Int): Int {
    return when (index) {
        0 -> x
        1 -> y
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
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
```

### The `in` convention

- `in` operator : contains

```kotlin
fun main() {
    val rect = Rectangle(Point(10, 20), Point(50, 50))
    println(Point(20, 30) in rect) // true
    println(Point(5, 5) in rect) // false
}

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}
```

### The rangeTo convention

- `..` operator : range, `rangeTo()` 호출
- e.g. `1..10` : `1`부터 `10`까지의 range

```kotlin
operator fun <T : Comparable<T>> T.rangeTo(that: T): ClosedRange<T>

fun main() {
    val now = LocalDate.now()
    val vacation = now..now.plusDays(10)
    println(now.plusWeeks(1) in vacation) // true

    (0..9).forEach { print(it) } // 0123456789
}
```

### The `iterator` convention for `for` loops

- `for (x in list) { ... }` : `list.iterator()`를 호출

```kotlin
operator fun CharSequence.iterator(): CharIterator

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
  object : Iterator<LocalDate> {
    var current = start
    override fun hasNext() =
      current <= endInclusive
    override fun next() = current.apply {
      current = plusDays(1)
    }
  }

fun main() {
    for (c in "abc") {
        println(c)
    }
  
  val newYear = LocalDate.ofYearDay(2021, 1)
    val daysOff = newYear.minusDays(1)..newYear.plusDays(1)
    for (dayOff in daysOff) {
        println(dayOff) // 2020-12-31, 2021-01-01, 2021-01-02
    }
}

```

## 4. Destructuring declarations and component functions

## 5. Reusing property accessor logic: delegated properties

## 6. Summary

