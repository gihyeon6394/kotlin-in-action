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

## 3. Variance: generics and subtyping

## 4. Summary

