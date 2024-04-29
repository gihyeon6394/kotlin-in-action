package annotationsAndReflection

/**
 * @author gihyeon-kim
 */

fun test(list: List<*>) {
    @Suppress("UNCHECKED_CAST")
    val strings = list as List<String>
}
