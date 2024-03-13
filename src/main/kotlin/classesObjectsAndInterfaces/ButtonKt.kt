package classesObjectsAndInterfaces

class ButtonKt : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {
        // ...
    }

    // static nested class
    class ButtonState : State {
        // ...
    }
}