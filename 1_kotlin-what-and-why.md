# 1. Kotlin: what and why

1. A taste of Kotlin
2. Kotlin's primary traits
3. Kotlin applications
4. The philosophy of Kotlin
5. Using the Kotlin tools
6. Summary

> This chapter covers
>
> - kotlin 기본 형태
> - Kotlin 언어 메인 특징
> - server-side, Android에서 가능
> - 다른 언어와의 차이점
> - Kotlin으로 작성하고 실행하기

---

- Kotlin은 Java 플랫폼을 위한 프로그램 언어
- 간결, 안전, 실용, Java Code와의 호환
- Java가 쓰이는 대부분의 server-side 개발, Android 앱에서 사용 가능
- 기존 Java 라이브러리, 프레임웤과 호환 (동일 성능으로)

## 1. A taste of Kotlin

````kotlin
data class Person(val name: String, val age: Int? = null)

fun main(args: Array<String>) {
    val persons = listOf(Person("Karina"), Person("Hani", age = 20))
    val oldest = persons.maxBy { it.age ?: 0 }

    println("The oldest is: $oldest")
}
````

## 2. Kotlin's primary traits

### Target platforms : server-side, Android, anywhere Java runs

### Statically typed

### Functional and object-oriented

### Free and open source

## 3. Kotlin applications

### Kotlin on the server side

### Kotlin on Android

## 4. The philosophy of Kotlin

### Pragmatic

### Concise

### Safe

### Interoperable

## 5. Using the Kotlin tools

### Compiling Kotlin code

### Plug-in for INtelliJ IDEA and Android Studio

### Interactive shell

### Eclipse plug-in

### Online playground

### Java-to-Kotlin converter

## 6. Summary
