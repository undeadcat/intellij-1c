@file:JvmName("CoreUtils")

package coreUtils

import org.picocontainer.PicoContainer
import java.util.*

fun String.equalsIgnoreCase(other: String): Boolean {
    return this.equals(other, true)
}

fun <T, TKey, TValue> Iterable<T>.toMap(keyFunc: (T) -> TKey, valueFunc: (T) -> TValue): HashMap<TKey, TValue> {
    val result = HashMap<TKey, TValue>()
    for (el in this) result.put(keyFunc(el), valueFunc(el))
    return result
}

fun <T> Iterable<T>.isEquivalentTo(other: Iterable<T>, comparison: (T, T) -> Boolean = { x, y -> x == y }): Boolean {
    val thisList = this.toMutableList()
    if (this.count() != other.count())
        return false
    for (el in other) {
        val found = thisList.find { comparison(it, el) }
        if (found == null)
            return false
        thisList.remove(found)
    }
    return true
}

fun parseIntOrNull(string: String): Int? {
    try {
        return Integer.parseInt(string)
    } catch (e: NumberFormatException) {
        return null
    }
}

fun <T> PicoContainer.getComponentsOfType(clazz: Class<T>): List<T> {
    return this.getComponentInstancesOfType(clazz).map { uncheckedCast<T>(it) }.toList()
}

fun <T> nullableCast(obj: Any?): T? {
    @Suppress("UNCHECKED_CAST")
    return obj as? T
}

fun <T> uncheckedCast(obj: Any?): T {
    @Suppress("UNCHECKED_CAST")
    return obj as T
}
