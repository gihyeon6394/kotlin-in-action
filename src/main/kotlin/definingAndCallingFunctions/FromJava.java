package definingAndCallingFunctions;

public class FromJava {

    public static void main(String[] args) {
        System.out.println(ExExtensionKt.lastChar("Call Kotlin Extension Function"));

        String[] arr = "12.345-6.A".split(".");
        for (String s : arr) {
            System.out.println(s); // empty string
        }
    }
}
