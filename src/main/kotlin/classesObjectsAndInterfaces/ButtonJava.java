package classesObjectsAndInterfaces;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ButtonJava implements View {
    @NotNull
    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(@NotNull State state) {
        // ...
    }

    public class ButtonState implements State {
        // ...
    }

    public static void main(String[] args) throws IOException {
        State buttonState = new ButtonJava().getCurrentState();

        byte[] serializedButtonState;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(buttonState); // Error: java.io.NotSerializableException
                baos.toByteArray();
            }
        }
    }
}
