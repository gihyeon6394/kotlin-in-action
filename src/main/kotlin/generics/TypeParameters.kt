package generics

fun <T> List<T>.filter(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        if (predicate(item)) {
            result.add(item)
        }
    }
    return result
}

val <T> List<T>.penultimate: T get() = this[size - 2]

