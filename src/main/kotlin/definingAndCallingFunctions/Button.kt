package definingAndCallingFunctions

class Button : View() {

    override fun show() {
        println("Button.show() is called")
    }
}

fun Button.outFun() {
    println("Button.kt outFun() is called")
}

fun main() {
    val button: View = Button()
    button.show() // Button.show() is called

    val view: View = Button()
    view.outFun() // View.kt outFun() is called
}