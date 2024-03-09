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

### Named arguments

### Default parameter values

### Getting rid of static uitility classes: top-level functions and properties

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