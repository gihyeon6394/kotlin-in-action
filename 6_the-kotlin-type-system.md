# 6. The Kotlin type system

1. Nullability
2. Primitive and other basic types
3. Collections and arrays
4. Summary

> ### This chapter covers
>
> - Nullable types, `null`
> - Primitive types, Java와 대응
> - Kotlin collections and Java

---

## 1. Nullability

- modern language는 NPE를 runtime에서 compile-time으로 옮기는 방향으로 발전

### Nullable types

![img_18.png](img_18.png)

- Kotlin은 명시적으로 _nullable type_을 지원
    - null 허용 여부를 type에 명시적으로 표시

```kotlin
fun strLen(s: String) = s.length

strLen(null) // compile error

fun strLenSafe(s: String?) = s?.length ?: 0
```

### THe meaning of types

- type이란, 해당 타입에 가능한 값과 연산의 집합
- java의 String은 null이거나 String instance를 가질 수 있음
    - `instanceof`로 null 여부를 확인해야 함
    - `@NotNull`, `Optional` 등 사용 가능
- nullable, not-null 은 런타임에는 타입이 동일
    - 컴파일 타임에 null 여부를 체크

### Safe call operator: `?.`

![img_19.png](img_19.png)

- `?.` = `null` check + operation
- `s?.length` = `if (s != null) s.length else null`

### Elvis operator: `?:`

- `?:` = `null` check + default value

```kotlin
fun strLenSafe(s: String?) = s?.length ?: 0

fun printShippingLabel(person: Person) {
    val address = person.company?.address
        ?: throw IllegalArgumentException("No address")
    with(address) { // address is not null
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}
```

### Safe casts: `as?`

![img_20.png](img_20.png)

- `as` : cast 실패 시 `ClassCastException` 발생
- `as?` : cast 실패 시 `null` 반환

```kotlin
class Person(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        val otherPerson = o as? Person ?: return false
        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
        firstName.hashCode() * 37 + lastName.hashCode()
}
```

### Not-null assertion: `!!`

![img_21.png](img_21.png)

- `!!` : `null`이 아님을 단언
- function 호출 전에 null 체크를 했을 경우 유용
- 2개 이상의 `!!` 을 사용하지 말 것
    - exception이 발생한 경우, 어디서 발생했는지 추적이 어려움 (line number를 추적함)

```kotlin
fun ignoreNulls(s: String?) {
    val notNullS: String = s!!
    println(notNullS.length)
}

ignoreNulls(null) // NPE

person.company!!.address!!.city // bad practice (NPE 발생 시 뭐가 null인지 추적 어려움)
```

### The `let` function

### Late initialized properties

### Extensions for nullable types

### Nullability of type parameters

### Nullability and Java

## 2. Primitive and other basic types

## 3. Collections and arrays

## 4. Summary