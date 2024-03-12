package classesObjectsAndInterfaces


fun main() {
    val button = Button()
    button.click()
    button.showOff()
}


interface Clickable {
    fun click() {
        println("Clickable clicked")
    }

    fun showOff() = println("I'm clickable!")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("Button clicked")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}


open class RichButton : Clickable {
    fun disable() {}
    open fun animate() {}
    override fun click() {}
}

// asbtract class : cannot be instantiated
abstract class Animated {
    // abstract function : must be overridden in subclasses
    abstract fun animate()

    // open function
    open fun stopAnimating() {}

    // This function is final and cannot be overridden in subclasses
    fun animateTwice() {}
}