# 3. Defining and calling functions

1. Creating collections in Kotlin
2. Making functions easier to call
3. Adding methods to other people’s classes: extension functions and properties
4. Working with collections: varargs, infix calls, and library support
5. working with strings and regular expressions
6. Summary

> ### This chapter covers
>
> - collection, string, regular expression으로 함수 정의
> - named arguments, default parameter values, infix call 문법
> - extension functions and properties를 통해 Java 라이브러리 사용
> - top-level, local function, property 정의

---

## 1. Creating collections in Kotlin

```kotlin
val set = hashSetOf(1, 7, 53)
val list = arrayListOf(1, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

println(set.javaClass) // class java.util.HashSet (Java의 getClass와 동일)
```

- Kotlin collection은 Java standard Java collection을 사용

## 2. Making functions easier to call

- 기본적으로 Collection은 `toString()` 메서드를 오버라이드하여 출력
- Guava, Apache Commons나 직접 커스터마이징 가능
- Kotlin은 standard library로 커스터마이징

```kotlin
fun <T> joinToString(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

val aespa = listOf("Karina", "Winter", "NingNing", "Giselle")
println(aespa.joinToString("; ", "(", ")"))
```

### Named arguments

```kotlin
val aespa = listOf("Karina", "Winter", "NingNing", "Giselle")
println(aespa.joinToString("; ", "(", ")"))
println(aespa.joinToString(separator = "; ", prefix = "(", postfix = ")"))
```

- Named arguments를 사용하여 함수 호출 시 인자의 이름을 명시적으로 지정 가능 (가독성 향상)

### Default parameter values

- 파라미터의 경우의수만큼 오버로딩해야하는 Java 문제를 해결
- Kotlin은 default parameter value를 지원
- 파라미터 순서대로 나열하되 중간에 생략하면 default value로 대체

```kotlin
val aespa = listOf("Karina", "Winter", "NingNing", "Giselle")
println(aespa.joinToString()) // Karina, Winter, NingNing, Giselle
println(aespa.joinToString(", ")) // Karina, Winter, NingNing, Giselle
````

> #### Default values and Java
>
> - Java에서는 default parameter value를 지원하지 않음
> - Java에서 Kotlin 함수를 호출할 때는 모든 파라미터를 명시적으로 지정해야함
> - `@JvmOverloads` annotation을 사용하여 Java에서도 default parameter value를 사용할 수 있음

### Getting rid of static uitility classes: top-level functions and properties

| Java                                               | Kotlin                                                                                |
|----------------------------------------------------|---------------------------------------------------------------------------------------|
| 무의미한 `Util` 클래스가 점점 거대해짐  e.g. `Collections` class | top-level function과 property를 사용하여 `Util` 클래스를 없앰<br/>static method를 위한 클래스 선언을 안해도 됨 |

- kotlin compiler가 top-level function이 담긴 파일명으로 class를 만듦
    - top-level function들은 static method가 됨

```java
/* Java */
package strings;

public class JoinKt {
    public static String joinToString() {
        // ...
    }
}
```

```kotlin
/* Kotlin */
package strings

fun joinToString() {
    // ...
}
```

```
/* Java */
import strings.JoinKt;

// ...
JoinKt.joinToString(list, ", ", "", "");
```

````
@file:JvmName("StringFunctions")

package strings

fun joinToString(...): String {
    ...
}

/* Java */
import strings.StringFunctions;
StringFunctions.joinToString(list, ", ", "", "");
````

#### TOP-LEVEL PROPERTIES

- function, proerty 모두 top-level로 선언 가능

```kotlin
package something

var ageKarina = 21 // static field
const val MAX_AGE = 100 // public static final int MAX_AGE = 100;

fun printAgeOfKarina() {
    println("Karina is $ageKarina years old")
}
```

## 3. Adding methods to other people’s classes: extension functions and properties

- Java와 Kotlin이 한 프로젝트에 함께 사용 가능
- _extension function_ : 클래스 밖에서 정의되어 클래스의 멤버처럼 호출 가능
    - body에서 private, protected 멤버에 접근 불가

![img_7.png](img_7.png)

```kotlin
package definingAndCallingFunctions

fun String.lastChar(): Char = this.get(this.length - 1) // this 생략 가능

fun main() {
    println("Kotlin".lastChar()) // n
}
```

- `String` : receiver type
- `"Kotlin"` : receiver object

### imports and extensions functions

- extension function을 사용하려면 import 필요
- `as` 키워드를 사용하여 이름 충돌 해결 가능
    - `import definingAndCallingFunctions.lastChar as last` : `last`로 호출

```kotlin
package definingAndCallingFunctions.innerPack

import definingAndCallingFunctions.lastChar

fun main() {
    println("Kotlin".lastChar())
}
```

### Calling extension functions from java

```java
public class Tmp {

    public static void main(String[] args) {
        System.out.println(ExExtensionKt.lastChar("Call Kotlin Extension Function"));
    }
}
```

### Utility functions as extensions

```kotlin
fun <T> Collection<T>.joinToString(separator: String = ", ", prefix: String = "", postfix: String = ""): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun Collection<String>.join(separator: String = ", ", prefix: String = "", postfix: String = "") =
    joinToString(separator, prefix, postfix)
```

- Gneric을 사용하지 않고, 특정 타입만 지원하도록 할 수 있음

### No overriding for extension functions

- extension function은 overriding 불가

````kotlin
open class View {

    open fun show() {
        println("View.show() is called")
    }
}

fun View.outFun() {
    println("View.kt outFun() is called")
}

class Button : View() {

    override fun show() {
        println("Button.show() is called")
    }
}

fun Button.outFun() {
    println("Button.kt outFun() is called")
}

fun main() {
    val button: View = Button()
    button.show() // Button.show() is called

    val view: View = Button()
    view.outFun() // View.kt outFun() is called
}
```` 

- `outFun`은 `View` 클래스에 추가된 것이지만, `Button` 클래스에서 overriding 불가

### Extension properties

````kotlin
fun String.lastChar(): Char = this.get(this.length - 1) // extension function

val String.lastChar: Char get() = get(length - 1) // extension property

fun main() {
    println("Kotlin".lastChar())
    println("Aespa".lastChar)
}
````

- getter 함수를 정의하여 extension property를 만들 수 있음

## 4. Working with collections: varargs, infix calls, and library support

- `varags` : 함수가 임의의 개수의 인자를 받을 수 있도록 함
- _infix_ : 함수를 중위 호출로 사용 가능
- _Desturcturing declarations_ : 객체의 속성을 여러 변수에 할당 가능

### Extending the Java Collections API

- Java의 Collectiion API 확장
- `last()`, `maxOrNull()` 등의 함수를 사용할 수 있음 (**extension function**)

```kotlin

fun main() {
    val aespa: List<String> = listOf("Karina", "Giselle", "Winter", "Ningning")
    println(aespa.last())

    val numbers: Collection<Int> = setOf(1, 2, 3, 4, 5)
    println(numbers.maxOrNull())
}
```

### Varargs: functions that accept an arbitrary number of arguments

| Java                                                                                   | Kotlin                                                       |
|----------------------------------------------------------------------------------------|--------------------------------------------------------------|
| `...`<br/> `public void foo(String... strings) { ... }`<br/>모든 인자가 array에 패킹되어서 전달되어야함 | `varag` 접근제어자<br/> `fun foo(vararg strings: String) { ... }` |

```kotlin
package kotlin.collections

// ...

public fun <T> listOf(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()
```

- _spread operator_ : 배열을 인자로 전달할 때 사용

```kotlin
val list = listOf("args: ", *args)
```

### Working with paris: infix calls and destructuring declarations

````
aespa to "Next Level"
newJeans to "Attention"

aespa.to("Next Level")
newJeans.to("Attention")
````

- `to` 함수는 infix 함수로 정의되어 있음
- _destructuring declaration_ : 객체의 속성을 여러 변수에 할당

```kotlin
package kotlin

public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

// ...

fun main() {
    val (groupName, songName) = "aespa" to "Next Level" // destructuring declaration
    println("groupName: $groupName, songName: $songName")

    val groupWithLeader = mapOf("aespa" to "Karina", "Red Velvet" to "Irene", "NewJeans" to "Minzi")
    for ((group, leader) in groupWithLeader) {
        println("$leader is the leader of $group")
    }
}
```

## 5. working with strings and regular expressions

### Splitting strings

## Regular expressions and triple-quoted strings

### Multiline triple-quoted strings

## 6. Summary