package programmingWithLamdas

import java.io.File

fun main() {
    val list = listOf(1, 2, 3, 4)
    println(list.filter { it % 2 == 0 }) // [2, 4], return a new list
    println(list.map { it * it }) // [1, 4, 9, 16], return a new list

    val idols = listOf(Idol("Karina", 23), Idol("Giselle", 24), Idol("Winter", 22), Idol("Ningning", 20))
    println(idols.filter { it.age > 20 }.map { it.name }) // 20살 이상인 아이돌의 이름만 출력

    val isAdult = { p: Idol -> p.age > 20 }

    println(idols.all(isAdult)) // false
    println(idols.any(isAdult)) // true
    println(idols.count(isAdult)) // 3

    println(idols.filter(isAdult).size) // 3 (create a new list and count the size)
    println(idols.count(isAdult)) // 3 (count the number of elements that satisfy the predicate)

    println(idols.find { it.name == "Karina" }) // Idol(name=Karina, age=23)
    println(idols.find { it.age > 20 }) // first element that satisfy the predicate

    val idols2 = listOf(
        Idol("Karina", 23), Idol("Giselle", 23),
        Idol("Minzi", 22), Idol("Alice", 23),
        Idol("Winter", 22)
    )

    println(idols2.groupBy { it.age })

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

    // flatten
    val list2 = listOf(listOf(1, 2, 3), listOf(4, 5, 6))
    println(list2.flatten()) // [1, 2, 3, 4, 5, 6]

    idols.map(Idol::name).filter { it.startsWith("K") }.forEach(::println) // Karina

    idols.asSequence().map(Idol::name).filter { it.startsWith("K") }.forEach(::println) // Karina

    listOf(1, 2, 3, 4).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList() // terminal operation : 실행


    idols.map(Idol::name) // map first
        .filter { it.startsWith("K") }
        .forEach(::println)

    idols.asSequence()
        .filter { it.name.startsWith("K") }
        .map(Idol::name)
        .forEach(::println)

    // create sequence
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    val sum = numbersTo100.sum() // sum() is a terminal operation

}

data class Idol(val name: String, val age: Int)

data class Member(val name: String, val age: Int)

data class GroupIdol(val name: String, val members: List<Member>)

fun File.isInsideHiddenDirectory() =
    generateSequence(this) { it.parentFile }.any { it.isHidden }