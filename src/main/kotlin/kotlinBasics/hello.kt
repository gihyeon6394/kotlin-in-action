package kotlinBasics


fun main(args: Array<String>) {
    println("Hello, World!")
    println("max of 0 and 42 is ${max(0, 42)}")

    val nameKarina = "Karina"
    val ageKarina = 23

    val ageKarina2: Int = 23 // no type inference

    val ageKarina3: Int
    ageKarina3 = 23 // no type inference

    val langs = arrayListOf("Java", "Kotlin", "Scala")
    langs.add("Python")
}

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b // traditional way, block body
}

fun max2(a: Int, b: Int): Int = if (a > b) a else b // expression body

fun max3(a: Int, b: Int) = if (a > b) a else b // expression body with type inference

fun printName(name: String) {
    println("Name: ${if (name.length > 0) name else "Unknown"}")
}

