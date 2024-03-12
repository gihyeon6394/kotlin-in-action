package definingAndCallingFunctions


fun main() {
    val aespa: List<String> = listOf("Karina", "Giselle", "Winter", "Ningning")
    println(aespa.last())

    val numbers: Collection<Int> = setOf(1, 2, 3, 4, 5)
    println(numbers.maxOrNull())

    val (groupName, songName) = "aespa" to "Next Level"
    println("$groupName - $songName")

    val groupWithLeader = mapOf("aespa" to "Karina", "Red Velvet" to "Irene", "NewJeans" to "Minzi")
    for ((group, leader) in groupWithLeader) {
        println("$leader is the leader of $group")
    }

}