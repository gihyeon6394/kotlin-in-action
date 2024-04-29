# 10. Annotations and reflection

1. Declaring and applying annotations
2. Reflection: introspecting Kotlin objects at runtime
3. Summary

> ### This chapter covers:
>
> - Applying and defining annotations
> - Using reflection to introspect classes at runtime
> - Kotlin project 실 예시

---

- annotation 사용 문법은 Java와 유사
- annotaion 선언 문법은 Java와 다소 다름
- 일반적인 reflection API는 Java와 유사하나, 세부적으로 조금 다름

## 1. Declaring and applying annotations

- annotation은 선언과 동시에 _metadata_ 를 추가하는 방법
    - _metadata_ : 어노테이션 설정에 따라 런타임에 사용될 수 있는 정보

### Applying annotations

- `@` 문자 사용

- 파라미터 가능 타입 : primitive, String, enum, class reference, another annotation, array
- `@MyAnnotation(MyClass::class)` : `MyClass` 클래스의 참조를 전달
- annotation을 인자로 전달 시 `@`를 붙이지 않음
    - `@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))`
- `arrayOf` 로 배열 전달 가능
    - `@RequestMapping(path = arrayOf("/foo", "/bar"))`

```kotlin
import org.junit.*

class MyTest {
    @Test
    fun testTrue() {
        assertTrue(true)
    }
}
```

- `@Deprecated` : Java와 동일
    - `replaceWith` : 대체할 함수를 지정할 수 있음
    - IntelliJ IDEA에서 deprecated된 함수를 사용하려하면 대체할 함수를 제안해줌

````
@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int) { ... }
````

- 어노테이션 argument는 compile-time에 특정 가능해야함
    - `const` modifier를 사용하면 compile-time에 상수로 사용 가능

````
const val TEST_TIMEOUT = 100L // compile-time 상수

@Test(timeout = TEST_TIMEOUT) fun testMethod() { ... }
````

### Annotation targets

![img_44.png](img_44.png)

- _use-site target_ annotation : 어노테이션을 어디에 적용할지 지정
- e.g. JUnit에서 `TemporaryFolder` rule을 적용해 test가 종료되면 임시 생성 파일, 폴더를 삭제
- target 목록
    - `property` : Java 어노테이션은 property에는 적용 불가
    - `field` : property를 위해 생성된 필드
    - `get` : property getter
    - `set` : property setter
    - `receiver` : 확장 함수나 프로퍼티의 수신 객체 파라미터
    - `param` : 생성자 파라미터
    - `setparam` : setter 파라미터
    - `delegate` : 위임 프로퍼티의 위임 인스턴스
    - `file` : 파일 전체

```kotlin
class HasTempFolder {
    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun testUsingTempFolder() {
        val createdFile = folder.newFile("myfile.txt")
        val createdFolder = folder.newFolder("subfolder")
        // ...
    }
}
```

```
fun test(list: List<*>) {
  @Suppress("UNCHECKED_CAST")
  val strings = list as List<String>
  // ...
}
```

- 어노테이션을 임의의 표현식에도 사용 가능 e.g. `@Suppress`

### Using annotations to customize JSON serialization

### Declaring annotations

### Meta-annotations: controlling how annotations is processed

### Classes as annotation parameters

### Generic classes as annotation parameters

## 2. Reflection: introspecting Kotlin objects at runtime

## 3. Summary

