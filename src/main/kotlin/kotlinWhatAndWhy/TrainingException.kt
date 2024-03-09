package kotlinWhatAndWhy

import java.lang.IllegalArgumentException
import java.util.Random


fun main(args: Array<String>) {
    val percentage = args[0].toInt()

    if (percentage !in 0..100) {
        throw IllegalArgumentException("A percentage value must be between 0 and 100: $percentage")
    }

    val percentage2 = if (args[1].toInt() in 0..100)
        args[1].toInt()
    else
        throw IllegalArgumentException("A percentage value must be between 0 and 100: ${args[1]}")

}