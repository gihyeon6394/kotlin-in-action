package definingAndCallingFunctions

open class View {

    open fun show() {
        println("View.show() is called")
    }
}

fun View.outFun() {
    println("View.kt outFun() is called")
}