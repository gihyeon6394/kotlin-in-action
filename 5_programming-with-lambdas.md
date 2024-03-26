# 5. Programming with lambdas

1. Lamda expressions and member references
2. Functional APIs for collections
3. Lazy collection operations: sequences
4. Using Java functional interfaces
5. Lanmdas with receiver: "with" and "apply"
6. Summary

> ### This chapter covers
>
> - Lambda expressions and member references
> - funcitonal style로 collection을 다루는 방법
> - Sequences : lazy collection operations
> - Java functional interfaces 사용
> - receiver를 사용한 lamda

---

## 1. Lambda expressions and member references

### Introduction to lamdas: blocks of code as function parameters

- "event 가 발생하면 이 handler를 실행해라" 라는 코드를 작성할 때, handler를 lamda로 표현 가능
    - 이전 Java에서는 anonymous class를 사용했음
- funtional-programming : funtion을 value처럼 다루는 것
- lamda : funciton을 선언하지 않아도, value처럼 그떄그떄 생성해서 전달 가능

```
button.setOnCLickListner (new OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d(TAG, "Button clicked");
    }
});

// lamda
button.setOnClickListener { Log.d(TAG, "Button clicked") }
```

### Lamdas and collections

```kotlin
fun main() {
    val peoples = listOf(Person("Alice", 29), Person("Bob", 31))
    findTheOldest(peoples)

    // with kotlin lamda
    println(peoples.maxBy { it.age })
    println(peoples.maxBy(Person::age))  // member reference
}

data class Person(val name: String, val age: Int)

fun findTheOldest(peoples: List<Person>) {
    var maxAge = 0
    var theOldest: Person? = null
    for (person in peoples) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}
```

- `{ it.age }` : lamda expression

### Syntax for lamda expressions

![img_14.png](img_14.png)

````
val sumEx = { x: Int, y: Int -> x + y }
println(sumEx(1, 2))

println({x: Int, y: Int -> x + y}(1, 2)) // Redundant lambda creation (bad)
 
run{println(42)} // run the code in the lambda

println(peoples.maxBy(){p: Person -> p.age}) // explicit type
println(peoples.maxBy{p: Person -> p.age}) // explicit type

println(peoples.maxBy{p -> p.age}) // implicit type
println(peoples.maxBy{it.age}) // implicit type (it : default parameter name)
````

### Accessing variables in scope

- lamda가 선언된 바깥의 변수를 사용할 수 있음

```kotlin
fun printMsgWithPrefix(msg: Collection<String>, prefix: String) {
    msg.forEach {
        println("$prefix $it")
    }
}


fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++ // non-final variable에 접근 가능
        } else if (it.startsWith("5")) {
            serverErrors++ // non-final variable에 접근 가능
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}
```

```kotlin
fun tryToCountButtonClicks(button: Button): Int {
    var clicks = 0
    button.onClick { clicks++ }
    return clicks
}
```

- 주의점 : 람다 안에서 변수 수정은 람다가 실행될 때만 일어남
- 위에서 `onClick` handler는 `tryToCountButtonClicks` 가 return 되고 난 후에 호출됨

### Member references

![img_15.png](img_15.png)

```kotlin
val getAge = Person::age

// same as
val getAge = { person: Person -> person.age }
````

- `::` : function 을 value로 사용할 수 있게 함

```kotlin
fun salute() = println("Salute!")
fun main() {
    run(::salute) // top level function reference
}
```

- _constructor reference_ : `::` 뒤에 생성자를 사용할 수 있음

```kotlin
// constructor reference
val createPerson = ::Person
val p = createPerson("Alice", 29)
println(p)

val aliceAgeFunc = p::age // bound method reference
println(aliceAgeFunc())
```

## 2. Functional APIs for collections

- collection을 다루기 위한 Kotlin standard library의 함수들

### Essentials: filter and map

- `filter` : collection의 element를 걸러내는 함수
- `map` : collection의 element를 변환하는 함수
    - 기존 collection을 변경하지 않고, 새로운 collection을 반환

```kotlin
val list = listOf(1, 2, 3, 4)

println(list.filter { it % 2 == 0 }) // [2, 4], return a new list
println(list.map { it * it }) // [1, 4, 9, 16], return a new list

val idols = listOf(Idol("Karina", 23), Idol("Giselle", 24), Idol("Winter", 22), Idol("Ningning", 20))
println(idols.filter { it.age > 20 }.map { it.name }) // 20살 이상인 아이돌의 이름만 출력
```

### "all", "any", "count", and "find": applying a predicate to a collection

```kotlin
val isAdult = { p: Idol -> p.age > 20 }

println(idols.all(isAdult)) // false
println(idols.any(isAdult)) // true
println(idols.count(isAdult)) // 3
```

#### `count` vs `size`

- `count` : predicate를 만족하는 element의 개수를 반환
- `size` : size를 알아내기위해 임시 collection을 만들어서 size를 반환

```kotlin
println(idols.filter(isAdult).size) // 3 (create a new list and count the size)
println(idols.count(isAdult)) // 3 (count the number of elements that satisfy the predicate)
````

---

```kotlin
println(idols.find { it.name == "Karina" }) // Idol(name=Karina, age=23)
println(idols.find { it.age > 20 }) // first element that satisfy the predicate
```

### groupBy: converting a list to a map of groups

```kotlin
val idols2 = listOf(
    Idol("Karina", 23), Idol("Giselle", 23),
    Idol("Minzi", 22), Idol("Alice", 23),
    Idol("Winter", 22)
)

println(idols2.groupBy { it.age }) // Map<Int, List<Idol>>
```

### flatMap and flatten: processing elements in nested collections

```kotlin
val memberAespa = listOf(
    Member("Karina", 23), Member("Giselle", 23),
    Member("Winter", 22), Member("Ningning", 20)
)

val memberRedVelvet = listOf(
    Member("Irene", 30), Member("Seulgi", 28),
    Member("Wendy", 27), Member("Joy", 25),
    Member("Yeri", 23)
)

val groupIdols = listOf(
    GroupIdol("aespa", memberAespa),
    GroupIdol("Red Velvet", memberRedVelvet)
)

println(groupIdols.flatMap { it.members }
    .map { it.name }) // [Karina, Giselle, Winter, Ningning, Irene, Seulgi, Wendy, Joy, Yeri]

val strings = listOf("abc", "def")
println(strings.flatMap { it.toList() }) // [a, b, c, d, e, f]

val list2 = listOf(listOf(1, 2, 3), listOf(4, 5, 6))
println(list2.flatten()) // [1, 2, 3, 4, 5, 6]
```

- `flatMap` = _transform_ -> _flatten_
    - _transform_ : 각 element를 주어진 함수를 통해 변환
    - _flatten_ : 변환된 결과를 하나의 list로 합침
- `flatten` : nested collection을 하나의 collection으로 합침

## 3. Lazy collection operations: sequences

- `filter()` : _eagerly_
    - 각 step 마다의 결과를 임시 list에 저장
- _Sequences_ : _lazily_
    - 각 step 마다의 결과를 저장하지 않고, 다음 step으로 넘어감
    - 임시 저장 객체 없음
    - 주의 : element가 많을 떄만 사용
    - Java Stream API

```kotlin
// eager
idols.map(Idol::name) // return a new list
    .filter { it.startsWith("K") } // return a new list
    .forEach(::println) // Karina

// lazy
idols.asSequence() // convert the list to a sequence
    .map(Idol::name)
    .filter { it.startsWith("K") }
    .forEach(::println) // Karina
```

- `asSequence()` : list를 sequence로 변환

### Executing sequence operations: intermediate and terminal operations

![img_16.png](img_16.png)

- _intermediate operation_ : sequence를 반환
- _terminal operation_ : sequence를 실행하고 결과 반환

```kotlin
listOf(1, 2, 3, 4).asSequence()
    .map { print("map($it) "); it * it }
    .filter { print("filter($it) "); it % 2 == 0 }
    .toList() // terminal operation : 실행
```

![img_17.png](img_17.png)

```kotlin
idols.map(Idol::name) // map first
    .filter { it.startsWith("K") }
    .forEach(::println)

// lazy하게 실행하면 map 연산 수가 줄어듦
idols.asSequence()
    .filter { it.name.startsWith("K") }
    .map(Idol::name)
    .forEach(::println)
```

### Creating sequences

```kotlin
val naturalNumbers = generateSequence(0) { it + 1 }
val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
val sum = numbersTo100.sum() // sum() is a terminal operation

fun File.isInsideHiddenDirectory() =
    generateSequence(this) { it.parentFile }.any { it.isHidden }

val file = File("/Users/svtk/.HiddenDir/a.txt")
println(file.isInsideHiddenDirectory())
```

## 4. Using Java functional interfaces

- Java functional interface를 사용할 수 있음
- _functional interface_ : 하나의 abstract method를 가지는 interface

```java
// functional interface
public interface OnClickListener {
    void onClick(View view);
}

public class Button {
}

```

```kotlin
button.setOnClickListener { view -> ... }
```

### Passing a lamda as a parameter to a Java method

- functional interface를 파라미터로 받는 모든 Java method에 lamda를 전달 가능
- Kotlin 1.0 까지는 모든 람다표현식은 익명 클래스로 컴파일됨 (인라인 람다 제외)
    - Java 8을 지원하기 시작한 뒤 컴파일러는 람다 표현식마다 .class 파일을 생성하지 않음
    - lamda에서 variable을 사용하면, 매 호출마다 새로운 인스턴스를 생성함

```kotlin
postponeComputation(1000) { println(42) }
postponeComputation(1000) { println(42) } // 기존의 익명 인스턴스를 재사용

// anonymous class
postponeComputation(1000, object : Runnable {
    override fun run() {
        println(42)
    }
})
```

기존의 익명 인스턴스 재사용

```kotlin
// handleComputation이 호출될 때마다 새로운 Runnable 인스턴스 생성
fun handleComputation(id: Int) {
    postponeComputation(1000) { println(id) } // variable 사용
}
```

컴파일러가 한 행동

````
class HandleComputation$1(val id: String) : Runnable {
    override fun run() {
        println(id)
    }
}

fun handleComputation(id: String) {
    postponeComputation(1000, HandleComputation$1(id))
}
````

### SAM constructors: explicit conversion of lamdas to functional interfaces

```java
public interface OnClickListener {
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    void onClick(View v);
}
```

- S(ingle) A(bstract) M(ethod) : 하나의 abstract method
- _SAM constructors_ : lamda를 functional interface로 변환하는 방법
- 컴파일러가 자동 변환을 지원하지 않을 때 사용
- 람다 인자 하나만 받음 : functional interface의 body로 사용

```kotlin
fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") } // SAM Constructor로 Runnable 생성
}

val listener = OnClickListener { view ->
    val text = when (view.id) {
        R.id.button1 -> "First button"
        R.id.button2 -> "Second button"
        else -> "Unknown button"
    }
    toast(text)
}

// listener를 button1, button2에 등록
button1.setOnClickListener(listener)
button2.setOnClickListener(listener)
```

## 5. Lanmdas with receiver: "with" and "apply"

```kotlin
// buildString : StringBuilder를 생성하고, lamda를 실행한 뒤, toString()을 호출하여 결과를 반환
fun alphabet() = buildString {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }
```

- _lambda with receiver_ : lamda를 호출할 때, receiver를 지정할 수 있음

### The "with" function

```kotlin
fun alphabet(): Stirng {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

fun alphabetBetter(): String {
    val strBuilder = StringBuilder()
    return with(strBuilder) {
        for (letter in 'A'..'Z') {
            this.append(letter) // this : strBuilder (receiver, this 생략 가능)
        }
        append("\nNow I know the alphabet!")
        toString()
    }
}

fun alphabetBest(): String = with(StringBuilder()) {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
    toString() // return
}
```

- `with`에 2개 인자 (receiver, lamda)를 전달
- 첫번째 인자를 lamda의 receiver로 사용
- lamda body에서 `this`를 사용하면 receiver를 참조 (생략 가능)
- `this@label` : lamda에서 다른 scope의 변수를 참조할 때 사용
    - e.g. `this@OuterClass` : OuterClass의 this

### The "apply" function

- `apply` : `with`와 비슷하지만, receiver object를 반환

```kotlin
fun alphabetApply(): String = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

fun createViewWithCustomAttr(context: Context) =
    TextView(context).apply {
        text = "Hello"
        textSize = 20.0
        setPadding(10, 0, 0, 0)
    }
```

- 인스턴스화시 특정 프로퍼티를 초기화하고 싶을 때 유용
    - Java의 Builder pattern과 유사

## 6. Summary