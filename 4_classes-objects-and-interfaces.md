# 4.Classes, objects, and interfaces

1. Defining class hierarchies
2. Declaring a class with nontrivial constructors or properties
3. Compiler-generated methods: data classes and class delegation
4. The "object" keyword: declaring a class and creating an instance, combined
5. Summary

> ### This chapter covers
>
> - 클래스, 인터페이스
> - Nontrivial constructors, properties¬
> - Data 클래스
> - Class delegation
> - `object` 키워드

---

## 1. Defining class hierarchies

- class 계층 구조
- Kotlin의 visiblity, access modifier
- `sealed` class

### Interfaces in Kotlin

| Java                    | Kotlin                    |
|-------------------------|---------------------------|
| `interface`             | `interface`               |
| `implements`, `extends` | `:`                       |
| `@Override`             | `override` (**required**) |

- Java 8 인터페이스와 비슷
- abstract method, non-abstract method 모두 정의 가능 (Java 8의 default method)
- `interface` keyword 로 선언

```kotlin
interface Clickable {
    fun click()
}

class Button : Clickable {
    override fun click() = println("I was clicked")
}
```

````kotlin
interface Clickable {
    // default method
    fun click() {
        println("Clickable clicked")
    }
}
````

```kotlin
interface Clickable {
    fun click() {
        println("Clickable clicked")
    }

    fun showOff() = println("I'm clickable!")
}

class Button : Clickable,
    Focusable { // compile error : Class 'Button' must override public open fun showOff(): Unit defined in classesObjectsAndInterfaces.Clickable because it inherits multiple interface methods of it
    override fun click() {
        println("Button clicked")
    }
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}
```

```kotlin

interface Clickable {
    fun click() {
        println("Clickable clicked")
    }

    fun showOff() = println("I'm clickable!")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("Button clicked")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}

```

### Open, final and abstract modifiers: final by default

- Java는 모든 클래스의 subclass 생성을 허용
    - _fragile base class_ : `final` 키워드가 없는 멤버들은 subclass에서 override 가능
- _fragile base class_ problem : 정확한 override 방법을 제공하지 않으면, 오버라이딩 시 문제 발생 가능성 있음
- _Effective Java_ 저자 Joshua Bloch : 모든 클래스를 `final`로 선언하라고 권장
    - 즉, 웬만하면 상속을 금하라
- 같은 목적으로 Kotlin은 `final` 이 default
    - `open` 키워드로 상속을 허용

```kotlin
// open class : 상속을 허용
open class RichButton : Clickable {

    // final method : override 금지
    fun disable() {}

    // open method : override 허용
    open fun animate() {}

    // override method : open (final 추가하면 비허용)
    override fun click() {}
}
```

```kotlin
// asbtract class : cannot be instantiated
abstract class Animated {
    // abstract function : must be overridden in subclasses
    abstract fun animate()

    // open function
    open fun stopAnimating() {}

    // This function is final and cannot be overridden in subclasses
    fun animateTwice() {}
}
```

### Visibility modifiers: public by default

| Java                        | Kotlin             |
|-----------------------------|--------------------|
| defualt : `package-private` | defualt : `public` |

- Visibility modifier : control access `public`, `internal`, `protected`, `private`
- defualt : `public`
- `internal` : module 내부에서만 접근 가능
- `private` 을 top-level 로 선언 가능 (class, function, property, etc.)
    - top-level `private` 은 해당 file 내에서만 접근 가능
- `protected` : class 내부나 subclass에서만 접근 가능 (Java와 다름)

```kotlin
internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}


fun TalkativeButton.giveSpeech() { // compile error : fun이 internal이거나, class가 public이어야 함
    yell() // compile error : private function
    whisper() // compile error : protected function
}
```

> #### Kotilin's visibility modifiers and java
>
> - `private` class는 컴파일 시 `package-private`로 변경
> - `internal` class는 컴파일 시 `public`으로 변경
> - 따라서 Java에서는 `internal` class에 접근 가능
> - 그러나, 복잡해지고, 충돌이 일어날 수 있으니 하지말 것

### Inner and nested classes: nested by default

![img_9.png](img_9.png)

- Kotlin은 outer class 인스턴스에 기본적으로 접근 불가능

```kotlin
import java.io.Serializable

interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}
``` 

```java

public class Button implements View {

    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(@NotNull State state) {
        // ...
    }

    public class ButtonState implements State {
        // ...
    }


    public static void main(String[] args) throws IOException {
        State buttonState = new ButtonJava().getCurrentState();

        byte[] serializedButtonState;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(buttonState); // Error: java.io.NotSerializableException !
                baos.toByteArray();
            }
        }
    }
}
```

- Java의 **문제점** `ButtonState` 타입의 인스턴스를 직렬화할때 `java.io.NotSerializableException` 발생
    - 원인 : `ButtonState`는 `Button`의 인스턴스에 대한 참조를 가지고 있음
    - 해결방법 : `ButtonState`를 `static`으로 선언해서 외부 클래스에 대한 암묵적 참조를 제거

```kotlin
class ButtonKt : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {
        // ...
    }

    // static nested class
    class ButtonState : State {
        // ...
    }
}
```

- `inner` 키워드를 사용하면 외부에 참조하는 Java 처럼 동작
- `inner` 키워드를 사용하지 않으면 `static` 처럼 동작

````kotlin
class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
````

### Sealed classes: defining restricted class hierarchies

```kotlin
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else -> // else 로 default case를 처리해야 함 (compile check)
            throw IllegalArgumentException("Unknown expression")
    }
````

- `sealed` class : class 계층 구조를 제한
    - `open`

![img_10.png](img_10.png)

```kotlin
sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(e: Expr): Int =
    when (e) {
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.right) + eval(e.left) // else 불필요
    }
```

- `sealed` class는 자신의 nested class만 상속 가능
    - `sealed` class에 클래스가 추가되면, 컴파일러는 모든 하위 클래스를 검사해서 `when` 식이 모든 경우를 다루는지 확인 (compile error)

## 2. Declaring a class with nontrivial constructors or properties

- _primary constructor_ : 클래스 선언과 함께 정의 (class body 바깥에)
- _secondary constructor_ : 클래스 내부에 정의
- _initializer block_ : 초기화 블록

### Initializing classes: primary constructor and initializer blocks

```kotlin
class User(val nickname: String)

// primary constructor를 풀어서 표현 (위와 동일)
class User constructor(_nickname: String) {
    val nickname: String

    // initializer block
    init {
        nickname = _nickname
    }
}

class User(_nickname: String) {
    val nickname = _nickname
}

class User(val nickname: String)
```

- `{}` 없음
- `()` : _primary constructor_ (`(val nickname: String)`)
- `constructor` 키워드 : priamry (secondary) constructor를 정의할 때 사용
- `init` 키워드 : _initializer block_ 을 정의
    - class가 생성될 때 primary constructor가 먼저 실행되고, 그 다음에 initializer block이 실행됨
-
    * 만일 모든 파라미터가 default value가 있으면, 컴파일러는 파라미터가 없는 생성자를 자도으로 생성

```kotlin
open class User(val nickname: String) {
    // ...
}
class TwitterUser(nickname: String) : User(nickname) {
    // ...
}
```

- sub-class는 super-class의 생성자 파라미터를 명시적으로 호출해야 함

```kotlin
open class Button

class RadioButton : Button()
```

- 생성자가 없으면, 파라미터가 없는 생성자가 default constructor로 생성됨
- interface는 생성자가 없으므로 sub-class에서 super-class 생성자를 호출할 필요가 없음

```kotlin
class Secretive private constructor() {}
```

- `private` 으로 인스턴스화 금지
    - single tone을 위한거면 Kotlin top-level 클래스를 사용하는 것이 좋음

### Secondary constructors: initializing the superclass in different ways

- Kotlin은 default parameter value를 지원 -> 생성자 오버로딩을 줄일 수 있음
- Kotlin에서 생성자 오버로딩이 필요한 경우 : 서로 다른 방법으로 클래스를 초기화하는 방법을 제공해야하는 sub-class를 만들 때

![img_11.png](img_11.png)

```kotlin
open class View {
    constructor(ctx: Context) {
        // some code
    }
    constructor(ctx: Context, attr: AttributeSet) {
        // some code
    }
}
class MyButton : View {
    constructor(ctx: Context)
            : super(ctx) {
        // ...
    }
    constructor(ctx: Context, attr: AttributeSet)
            : super(ctx, attr) {
        // ...
    }
}
```

![img_12.png](img_12.png)

```kotlin
class MyButton : View {
    constructor(ctx: Context)
            : this(ctx, MY_STYLE) {
        // ...
    }
    constructor(ctx: Context, attr: AttributeSet)
            : super(ctx, attr) {
        // ...
    }
}
```

### Implementing properties declared in interfaces

```kotlin
interface User {
    val nickname: String
}

// primary constructor
class PrivateUser(override val nickname: String) : UserInf

// custom getter
class SubscribingUser(val email: String) : UserInf {
    override val nickname: String
        get() = email.substringBefore('@')
}

// property initializer
class FacebookUser(val accountId: Int) : UserInf {
    override val nickname = getFacebookName(accountId)
}
```

- `User` 구현체는 `nickname` property를 제공해야 함
- `PrivateUser` : primary constructor를 통해 property를 제공 (`override` 키워드)
- `SubscribingUser` : custom getter를 통해 property를 제공
    - value를 저장하기 위한 backing field 없이 **getter() 호출마다** 값을 계산, 리턴
- `FacebookUser` : property initializer를 통해 property를 제공
    - property를 초기화하는 코드를 property 선언에 직접 넣음

````kotlin
interface User {
    val email: String // abstract property
    val nickname: String
        get() = email.substringBefore('@') // custom getter (default implementation)
}
````

### Accessing a backing field from a getter or setter

- backing field : property의 값을 저장하는 field
- 두 종류의 property
    - 종류 1. value를 저장하는 property
    - 종류 2. custome accesor 를 통해서 매번 호출마다 값을 계산하는 property
- 종류 2가지를 합쳐서 매번 계산하여 값을 저장하는 property를 만들 수 있음

```kotlin

class User(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println(
                """
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent()
            )
            field = value // update backing field
        }
}

fun main() {
    User("Alice").address = "Elsenheimerstrasse 47, 80687 Muenchen" // Address was changed for Alice:
    // "unspecified" -> "Elsenheimerstrasse 47, 80687 Muenchen".

}
```

### Changing accessor visibility

```kotlin
class LengthCounter {
    var counter: Int = 0
        private set // the setter is private and has no backing field

    fun addWord(word: String) {
        counter += word.length
    }
}

fun main() {
    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    println(lengthCounter.counter) // 3
}
```

## 3. Compiler-generated methods: data classes and class delegation

- `equals`, `hashCode`, `toString` 등의 메서드들이 중복으로 오버라이딩 되어있음
- kotlin compiler는 뒤에서 자동으로 생성해줌 (코드 작성 X)

### Universal object methods

- Java에서는 `equals`, `hashCode`, `toString` 등의 메서드를 오버라이딩 해야함

```kotlin
class Client(val name: String, val postalCode: Int)
```

#### STRING REPRESENTATION: toString()

```kotlin
class Client(val name: String, val postalCode: Int) {
    override fun toString() = "Client(name=$name, postalCode=$postalCode)"
}
```

#### OBJECT EQUALITY: equals()

- Kotlin은 `==` 을 컴파일 시 `equals()`로 변환

```kotlin
val client1 = Client("Alice", 342562)
val client2 = Client("Alice", 342562)
println(client1 == client2) // false
```

```kotlin
class Client(val name: String, val postalCode: Int) {
    override fun toString() = "Client(name=$name, postalCode=$postalCode)"

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client) return false
        return name == other.name && postalCode == other.postalCode
    }
}

val client1 = Client("Alice", 342562)
val client2 = Client("Alice", 342562)
println(client1 == client2) // true
```

### HASH CONTAINERS: hashCode()l

- `hashCode()` 는 `eqauls()` 가 오버라이딩 되면 무조건 오버라이딩 해야함

```kotlin
val processed = hashSetOf(Client("Alice", 342562))
println(processed.contains(Client("Alice", 342562))) // false
```

### Data classes: autogenerated implementations of universal methods

```kotlin
data class Client(val name: String, val postalCode: Int)
```

- `data` : `equals`, `hashCode`, `toString` 등의 메서드를 자동으로 생성해줌
- primary constructor에 선언된 property들만 포함

#### DATA CLASSES AND IMMUTABILITY: THE COPY() METHOD

- _immutuable_ 하게 data class를 작성하기를 권장
- object가 한번 만들어지면 다른 스레드에서 변경되지 않도록 보장하기 위함

```kotlin
class Client(val name: String, val postalCode: Int) {
    fun copy(name: String = this.name, postalCode: Int = this.postalCode) = Client(name, postalCode)
}
```

### Class delegation: using the "by" keyword

- Kotlin은 기본적으로 `open` 된 클래스에 대해서만 상속을 허용하도록 설계
- 상속이 금지된 클래스를 확장하고 싶으면 _Decorator pattern_ 을 사용
    - _Decorator pattern_ : original class의 인터페이스 구현체를 새로 만들어 original class의 인스턴스를 포함하는 방식

```kotlin
// ArrayList를 상속받지 않고, ArrayList의 기능을 확장
class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    override val size: Int get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>): Boolean =
        innerList.containsAll(elements)
}
```

```kotlin
// by 키워드 사용
class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {}
```

```kotlin
// by 키워드 사용해서 HashSet 기능 확장하기
class CountingSet<T>(
    val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {
    var objectsAdded = 0
    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }
    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}
```

## 4. The "object" keyword: declaring a class and creating an instance, combined

- `object` keyword : 클래스 선언과 인스턴스 생성을 동시에 수행
- _object declaration_ : singleton을 쉽게 만들 수 있음
- _Companion object_ : factory method와 static member를 제공
- _Object expression_ : Java 익명 내부 클래스 대체

### Object declarations: singletons made easy

- _class declaration_ + _single instance_
- property, method, initializer block, etc. 모두 포함 가능
    - 생성자만 불가능

```kotlin
object Payroll {
    val allEmployees = arrayListOf<Person>()
    fun calculateSalary() {
        for (person in allEmployees) {
            // ...
        }
    }
}
```

- 상속 가능

```kotlin
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(
            file2.path,
            ignoreCase = true
        )
    }
}
```

- class 안에 Object declaration
- class 마다 Object instance 생성되지 않음

```kotlin
data class Person(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int = p1.name.compareTo(p2.name)
    }
}
```

### Companion objects: a place for factory methods and static members

| Java          | Kotlin                                   |
|---------------|------------------------------------------|
| static method | package-level function, companion object |

- kotlin class는 static member를 가질 수 없음
- factory method : class 인스턴스 없이 메서드 실행이 가능해야하고, class 내부에 접근 가능해야할 때 사용

![img_13.png](img_13.png)

```kotlin
class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}
```

```kotlin

class UserFacebook1 {
    val nickname: String

    constructor(email: String) {
        nickname = email.substringBefore('@')
    }

    constructor(facebookAccountId: Int) {
        nickname = getFacebookName(facebookAccountId)
    }
}

class UserFacebook2 private constructor(val nickname: String) {
    companion object {
        // factory method 1
        fun newSubscribingUser(email: String) = UserFacebook2(email.substringBefore('@'))

        // factory method 2
        fun newFacebookUser(accountId: Int) = UserFacebook2(getFacebookName(accountId))
    }
}
```

### Companion objects as regular objects

```kotlin
class Person(val name: String) {
    // named companion object
    companion object Loader {
        fun fromJSON(jsonText: String): Person {
            // ...
        }
    }
}
```

#### IMPLEMENTING INTERFACES IN COMPANION OBJECTS

```kotlin
interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person(val name: String) {

    // companion object가 JSONFactory 인터페이스를 구현
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person {
            // ...
        }
    }
}
```

- `@JvmStatic` annotation : Java에서 companion object의 method를 static method로 사용

#### COMPANION-OBJECT EXTENSIONS

```kotlin
// buisness logic
class Person(val firstName: String, val lastName: String) {
    // empty companion object
    companion object
}

// extension function
fun Person.Companion.fromJSON(json: String): Person {
// ...
}
```

### Object expressions: anonymous inner classes rephrased

- _anonymous object_ : Java의 _anonymous inner class_ 를 대체
- Java와 다르게, 2개 이상의 인터페이스를 구현하거나 0개의 인터페이스를 구현 가능
- **싱글톤 아님**
- 여러개의 메서드를 오버라이딩 해야할 때 유용

```kotlin
window.addMouseListener(
    // declare anonymous object
    object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            // ...
        }

        override fun mouseEntered(e: MouseEvent) {
            // ...
        }
    }
)
```

- Java와 다르게, `final` 변수 외에도 접근, 수정 가능

```kotlin
fun countClicks(window: Window) {
    var clickCount = 0
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++ // access to the outer scope
        }
    })
}
```

## 5. Summary

- Kotlin의 인터페이스는 Java 8 default 메서드를 지원, property도 정의 가능
- default : `final` (상속 금지), `public` (접근 가능)
- `open` : non-`final` (상속 허용)
- `internal` : module 내부에서만 접근 가능
- nested class는 default로 inner class가 아님
    - `inner` 키워드를 사용하면 외부에 참조하는 Java 처럼 동작
- `sealed` class : class 계층 구조를 제한
- initializer block : 초기화 블록
    - secondary constructor로 생성자 오버로딩
- `field` : backing field
- `data` class : `equals`, `hashCode`, `toString` 등의 메서드를 자동으로 생성해줌
- class delegation : `by` 키워드로 코드 중복 제거
- object declaration : 코틀린에서 싱글톤을 만드는 방법
- companion object : factory method와 static member를 제공
- companion object는 인터페이스를 구현하고, extension function, property를 가질 수 있음
- object expression : Java의 _anonymous inner class_ 를 대체
    - 여러개의 메서드를 오버라이딩 해야할 때 유용
    - `final` 변수 외에도 접근, 수정 가능
    - 여러 인터페이스 구현 가능