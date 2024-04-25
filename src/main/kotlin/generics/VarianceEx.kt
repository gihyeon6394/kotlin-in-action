package generics

import kotlinWhatAndWhy.Num
import java.util.*
import kotlin.reflect.KClass

fun printContents(list: List<Any>) {
    println(list.joinToString())
}

fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}


fun <T> copyData(source: MutableList<out T>, destination: MutableList<T>) {
    for (item in source) {
        destination.add(item)
    }
}

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String) = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}

fun main() {
    val strings = listOf("abc", "bac")
    printContents(strings) // abc, bac

    val strs = mutableListOf("abc", "bac")
    // addAnswer(strs) // compile err : Type mismatch: inferred type is MutableList<String> but MutableList<Any> was expected

    val s: String = "abc"
    val t: String? = s

    // val ss: String = t // compile err : Type mismatch: inferred type is String? but String was expected

    val numbers = mutableListOf(1, 2, 3)
    val anyItems = mutableListOf<Any>()
    copyData(numbers, anyItems)
    println(anyItems) // [1, 2, 3]


    val list: MutableList<Any?> = mutableListOf("abc", 1, 2.0)
    val chars = mutableListOf('a', 'b', 'c')
    val unkownEl: MutableList<*> = if (Random().nextBoolean()) list else chars
    // unkownEl.add(42) // compile err : Unresolved reference. None of the following candidates is applicable because of receiver type mismatch: public abstract fun add(element: Nothing): Boolean defined in kotlin.collections.MutableList
    println("list : $list")

    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    // validators[String::class]!!.validate("") // compile err : Kotlin: Type mismatch: inferred type is String but Nothing was expected
}
