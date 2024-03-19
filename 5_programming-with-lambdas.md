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

### Essentials: filter and map

### "all", "any", "count", and "find": applying a predicate to a collection

### groupBy: converting a list to a map of groups

### flatMap and flatten: processing elements in nested collections

## 3. Lazy collection operations: sequences

### Executing sequence operations: intermediate and terminal operations

### Creating sequences

## 4. Using Java functional interfaces

### Passing a lamda as a parameter to a Java method

### SAM constructors: explicit conversion of lamdas to functional interfaces

## 5. Lanmdas with receiver: "with" and "apply"

### The "with" function

### The "apply" function

## 6. Summary