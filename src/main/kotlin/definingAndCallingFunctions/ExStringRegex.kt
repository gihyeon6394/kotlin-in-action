package definingAndCallingFunctions

fun main() {
    println("12.345-6.A".split("\\.|-".toRegex())) // [12, 345, 6, A]

    println("12.345-6.A".split(".", "-")) // [12, 345, 6, A]

    parsePath("/Users/yole/kotlin-book/chapter.adoc")

    val logoKotling = """| //
                        .|//
                        .|/ \"""

    println(logoKotling.trimMargin("."))

    println("""${'$'}99.9""") // $99.9
    println("""C:\Users\yole\kotlin-book\chapter.adoc""") // C:\Users\yole\kotlin-book\chapter.adoc
}

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")

    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir: $directory, name: $fileName, ext: $extension")
}

fun parsePathRegex(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)

    if (matchResult != null) {
        val (directory, fileName, extension) = matchResult.destructured
        println("Dir: $directory, name: $fileName, ext: $extension")
    }
}