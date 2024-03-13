package classesObjectsAndInterfaces;

internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}


internal fun TalkativeButton.giveSpeech() {

}