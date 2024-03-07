package kotlinWhatAndWhy

import java.util.Random // import java standard lib

class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        /*get() {
            return height == width
        }*/
        get() = height == width
}

fun createRandomRectangle(): Rectangle {
    val random = Random()
    return Rectangle(random.nextInt(), random.nextInt())
}