# 9. Generics

1. Generic type parameters
2. Generics at runtime: erased and reified type parameters
3. Variance: generics and subtyping
4. Summary

> ### This chapter covers
>
> - declaring generic functions and classes
> - Type erasure and reified type parameters
> - Declaration-site and use-site variance

---

## 1. Generic type parameters

- _Reified type parameters_ : runtime에 generic type을 알 수 있게 해줌
    - 일반적으로 type argument는 런타임에 지워지기 떄문에 불가능함
- _Declaration-site variance_ : 클래스 선언 시 variance를 지정

```kotlin
val authors = listOf("Dmitry", "Svetlana") // kotlin compiler의 type inference로 List<String>로 추론
```

- Generic은 _type parameter_ 로 타입을 정의할 수 있게 해줌
- 인스턴스가 생성될 때 _type argument_ 를 제공해야 함
    - e.g. `val list: List<String> = listOf("a", "b", "c")`
- Kotlin은 무조건 명시적으로 혹은 추론하여 type argument를 제공해야 함 (_raw type_ 미지원)
    - Java 1.5 (Generics가 처음 도입된 버전)이전 버전과 호환을 위함
    - _raw type_ : type argument를 제공하지 않은 경우

### Generic functions and properties

![img_34.png](img_34.png)

```kotlin
val letters = ('a'..'z').toList()
println(letters.slice<Char>(0..2)) // [a, b, c]
println(letters.slice(10..13)) // [k, l, m, n]
```

- _generic function_ : type parameter를 가지는 함수
    - type paramter는 function 호출시 특정 type으로 지정되어야함

```kotlin
fun <T> List<T>.filter(predicate: (T) -> Boolean): List<T>
val <T> List<T>.penultimate: T get() = this[size - 2]


fun main() {
    val authors = listOf("Dmitry", "Svetlana")
    val readers = mutableListOf<String>(/* ... */)
    readers.filter { it !in authors }

    println(listOf(1, 2, 3, 4).penultimate) // 3
}
```

### Declaring generic classes

- class 이름 옆에 type parameter를 선언

```kotlin
interface List<T> {
    operator fun get(index: Int): T
}
```

![img_35.png](img_35.png)

```kotlin
interface Comparable<T> {
    fun compareTo(other: T): Int
}
class String : Comparable<String> {
    override fun compareTo(other: String): Int = /* ... */
}
```

### Type parameter constraints

![img_36.png](img_36.png)

- _Type parameter constraints_ : type parameter가 특정 type을 상속하거나 구현해야 함을 명시
    - `fun <T : Number> List<T>.sum(): T`
- _upper bound_ constraint : type parameter가 특정 type이거나 subtype이어야함
    - `fun <T : Number> List<T>.sum(): T`

```kotlin
fun <T : Number> List<T>.sum(): T


fun <T : Number> oneHalf(value: T): Double {
    return value.toDouble() / 2.0
}

fun <T : Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

fun <T> ensureTrailingPeriod(seq: T)
        where T : CharSequence, T : Appendable { // type parameter가 CharSequence와 Appendable을 상속해야함
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
}

fun main() {
    println(listOf(1, 2, 3).sum()) // 6
    println(oneHalf(3)) // 1.5
    println(max("kotlin", "java")) // kotlin

    val helloWorld = StringBuilder("Hello World")
    ensureTrailingPeriod(helloWorld)
    println(helloWorld) // Hello World.
}
```

### Making type parameters non-null

```kotlin
class Processor<T> {
    fun process(value: T) {
        value?.hashCode() // value is nullable
    }
}

class ProcessorNotNull<T : Any> {
    fun process(value: T) {
        value.hashCode() // value is non-null
    }
}
```

- upper bound를 생략하면 `: Any?` 가 생략된 것과 같음
- `: Any` 를 사용하면 non-null type parameter를 강제할 수 있음

## 2. Generics at runtime: erased and reified type parameters

- _type erasure_ : type arguments가 런타임에는 지워짐
- `inline` function을 사용하면 reified type parameter를 사용할 수 있음
    - type argument를 런타임에도 알 수 있게 해줌

### Generics at runtime: type checks and casts

![img_37.png](img_37.png)

- Kotlin도 Java 처럼 런타임에 generic이 지워짐
- 즉, 제네릭 클래스의 인스턴스는 런타임에 type argument를 알 수 없음
- 장점 : 타입 정보를 메모리에 가지고 있을 필요 없음 (리소스 절약)
- e.g. `List<String>` -> runtime에는 `List`로만 알 수 있음

```kotlin
if (value is List<String>) { // ERROR: Cannot check for instance of erased type
    // ...
}

if (value is List<*>) { // OK: type erasure
    // ...
}

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

    printSumBetter(
        listOf(
            "a",
            "b",
            "c"
        )
    ) // compile error : Type mismatch: inferred type is List<String> but Collection<Int> was expected
}
```

- _star projection_ : `List<*>` (모든 type argument를 받을 수 있음)

### Declaring functions with reified type parameters

```kotlin
fun <T> isA(value: Any) = value is T // ERROR: Cannot check for instance of erased type
```

- inline function은 reified type parameter를 사용할 수 있음
    - `inline` function : compiler가 function body를 호출하는 곳에 복사해 넣음
    - 람다와 사용하면 익명 클래스 생성을 줄여 성능 향상

```kotlin
inline fun <reified T> isA(value: Any) = value is T

fun main() {
    println(isA<String>("abc")) // true
    println(isA<String>(123)) // false

    // using filterIsInstance standard library function
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>()) // [one, three]
}

// filterIsInstance standard library function (축약)
inline fun <reified T> Iterable<*>.filterIsInstance(): List<T> {
    val destination = mutableListOf<T>()
    for (element in this) {
        if (element is T) { // reified type parameter
            destination.add(element)
        }
    }
    return destination
}
```

#### inline function에서만 reification이 가능한 이유

- `inline` function은 컴파일러가 호출한 곳에 바이트 코드를 넣음
- 즉, 인라인 함수를 호출한 곳마다 다른 바이트 코드가 생성됨 (컴파일러는 이미 type argument를 알고 있음)

### Replacting class references with reified type parameters

```kotlin
val serviceImpl = ServiceLoader.load(Service::class.java)
// better
val serviceImpl = loadService<Service>()

inline fun <reified T> loadService() {
    return ServiceLoader.load(T::class.java)
}
```

- `::class.java` : `java.lang.Class` 에 상응하는 Kotlin class 가져옴

### Restrictions on reified type parameters

- 가능
    - `is`, `!is`, `as`, `as?` 연산자 사용 (type check, cast)
    - `::class` 같은 Kotlin reflection API 사용
    - `java.lang.Class` 사용
    - 다른 function을 호출할 떄 argument로 사용
- 불가능
    - type parameter로서 정의된 클래스의 새로운 인스턴스 생성 (e.g. `T()`)
    - type parameter 클래스의 companion object에 접근
    - non-reified type parameter를 사용한 함수 호출
    - 클래스의 type parameter, property, non-inline function을 `reified`로 선언

## 3. Variance: generics and subtyping

## 4. Summary

