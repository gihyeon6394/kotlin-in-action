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

### imports and extensions functions

### Calling extension functions fromjava

### Utility functions as extensions

### No overriding for extension functions

### Extension properties

## 4. Working with collections: varargs, infix calls, and library support

### Extending the Java Collections API

### Varargs: functions that accept an arbitrary number of arguments

### Working with paris: infix calls and destructuring declarations

## 5. working with strings and regular expressions

### Splitting strings

## Regular expressions and triple-quoted strings

### Multiline triple-quoted strings

## 6. Summary