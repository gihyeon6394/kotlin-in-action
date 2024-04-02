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

## 3. Conventions used for collections and ranges

## 4. Destructuring declarations and component functions

## 5. Reusing property accessor logic: delegated properties

## 6. Summary

