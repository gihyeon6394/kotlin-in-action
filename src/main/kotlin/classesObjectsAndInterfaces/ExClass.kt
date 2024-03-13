package classesObjectsAndInterfaces

class User(val nickname: String)


fun main() {
    val alice = User("Alice")
    println(alice.nickname)

    UserWithSetter("Alice").address = "Elsenheimerstrasse 47, 80687 Muenchen"

    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    println(lengthCounter.counter)

}

class Secretive private constructor() {}


interface UserInf {
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

fun getFacebookName(accountId: Int): String {
    return "facebookName"
}

interface UserWithCustomGetter {
    val email: String // abstract property
    val nickname: String
        get() = email.substringBefore('@') // custom getter (default implementation)
}

class UserWithCustomGetterImpl(override val email: String) : UserWithCustomGetter

class UserWithSetter(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println(
                """
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent()
            )
            field = value
        }
}

class LengthCounter {
    var counter: Int = 0
        private set // the setter is private and has no backing field

    fun addWord(word: String) {
        counter += word.length
    }
}