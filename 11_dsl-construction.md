# 11. DSL construction

1. From APIs to DSLs
2. Building structured APIs: lamdas with receivers in DSLs
3. More fleible block nesting with the "invoke" convention
4. Kotlin DSLs in practice
5. Summary

> ### This chapter covers
>
> - dsl 만들기
> - reciver로 람다 사용하기
> - `invoke` convention
> - Kotlin DSLs 예제

---

## 1. From APIs to DSLs

- code의 가독성 : 의도가 명확
- 불필요한 문법 제거
- kotlin 은 _fully statically typed_ 언어
    - _fully statically typed_ : 모든 변수의 타입이 컴파일 시점에 결정

| regular syntax                     | Clean syntax                              | feature in use                  |
|------------------------------------|-------------------------------------------|---------------------------------|
| `StringUtil.capitalize("hello")`   | `"hello".capitalize()`                    | extension functions             |
| `1.to("one")`                      | `1 to "one"`                              | infix functions                 |
| `set.add("a")`                     | `set += "a"`                              | operator overloading            |
| `map.get("key")`                   | `map["key"]`                              | COnvention for the `get` method |
| `file.use({f->f.read()})`          | `file.use { read() }`                     | lambdas outside of parentheses  |
| `sb.append("yes") sb.append("no")` | `with(sb) { append("yes") append("no") }` | lambdas with receivers          |

### The concept of domain-specific languages

- _general-purpose programming language_ : 컴퓨터로 언어 레벨에서 모든 문제 해결 가능
- _domain-specific language_ : 특정 문제 (Task, Domain) 해결을 위한 언어
    - ex) SQL, HTML, CSS
    - 간결한 문법, declarative
- 단점 : host application과의 호환, 결합 문제
    - host app은 _general-purpose programming language_ 로 작성되어 즉시 프로그램에 임베디드
- 해결책 : _internal DSL_ (host language의 기능을 이용한 DSL)

### Internal DSLs

- _internal DSL_ : host language의 기능을 이용한 DSL

```sql
SELECT Country.name, COUNT(Customer.id)
FROM Country
         JOIN Customer
              ON Country.id = Customer.country_id
GROUP BY Country.name
ORDER BY COUNT(Customer.id) DESC LIMIT 1
```

```kotlin
// SQL 쿼리 작성 (= Task)에 특화된 Kotlin DSL의 라이브러리
(Country join Customer)
    .slice(Country.name, Count(Customer.id))
    .selectAll()
    .groupBy(Country.name)
    .orderBy(Count(Customer.id), isAsc = false)
    .limit(1)
```

### Structure of DSLs

````
dependencies { // lamda nesting
  compile("junit:junit:4.11")
  compile("com.google.inject:guice:4.1.0")
}
````

- Kotlin DSL은 주로 람다 중첩 (nesting), 메서드 연쇄 호출로 이루어짐

### Building HTMl with an internal DSL

- `kotlinx.html` 라이브러리 사용

```kotlin
fun createSimpleTable() = createHTML().table {
    tr {
        td { +"cell" }
    }
}

fun createAnotherTable() = createHTML().table {
    val numbers = mapOf(1 to "one", 2 to "two")
    for ((num, string) in numbers) {
        tr {
            td { +"$num" }
            td { +string }
        }
    }
}
```

```html

<table>
    <tr>
        <td>cell</td>
    </tr>
</table>

<table>
    <tr>
        <td>1</td>
        <td>one</td>
    </tr>
    <tr>
        <td>2</td>
        <td>two</td>
    </tr>
</table>
```

## 2. Building structured APIs: lamdas with receivers in DSLs

## 3. More fleible block nesting with the "invoke" convention

## 4. Kotlin DSLs in practice

## 5. Summary
