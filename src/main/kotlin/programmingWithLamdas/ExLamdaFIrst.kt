package programmingWithLamdas

fun main() {
    val peoples = listOf(Person("Alice", 29), Person("Bob", 31))
    findTheOldest(peoples)

    // with kotlin lamda
    println(peoples.maxBy { it.age })
    println(peoples.maxBy(Person::age))

    val sumEx = { x: Int, y: Int -> x + y }
    println(sumEx(1, 2))

    println({ x: Int, y: Int -> x + y }(1, 2)) // Redundant lambda creation
    run { println(42) } // run the code in the lambda

    println(peoples.maxBy() { p: Person -> p.age })

    // constructor reference
    val createPerson = ::Person
    val p = createPerson("Alice", 29)
    println(p)

    val aliceAgeFunc = p::age
    println(aliceAgeFunc())
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
            clientErrors++ // final variable에 접근 가능
        } else if (it.startsWith("5")) {
            serverErrors++ // final variable에 접근 가능
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}