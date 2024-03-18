package classesObjectsAndInterfaces

import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File

fun main() {
    val persons = listOf(Person("Bob"), Person("Alice"))
    println(persons.sortedWith(Person.NameComparator))

    val files = listOf(File("/Z"), File("/a"))
    println(files.sortedWith(CaseInsensitiveFileComparator))

    val subscribingUser = UserFacebook2.newSubscribingUser("bob@email.com")
    val facebookUser = UserFacebook2.newFacebookUser(4)

}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(
            file2.path,
            ignoreCase = true
        )
    }
}

data class Person(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int = p1.name.compareTo(p2.name)
    }
}

object Payroll {
    val allEmployees = arrayListOf<Person>()
    fun calculateSalary() {
        for (person in allEmployees) {
            // ...
        }
    }
}


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

fun countClicks(window: Window) {
    var clickCount = 0
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++ // access to the outer scope
        }
    })
}