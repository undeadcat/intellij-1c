@file:JvmName("CoreUtils")

package coreUtils

import org.picocontainer.PicoContainer
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.io.StringWriter
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
    return this.getComponentInstancesOfType(clazz).map { uncheckedCast<T>(it) }
}

fun <T> nullableCast(obj: Any?): T? {
    @Suppress("UNCHECKED_CAST")
    return obj as? T
}

fun <T> uncheckedCast(obj: Any?): T {
    @Suppress("UNCHECKED_CAST")
    return obj as T
}

fun Exception.formatStacktrace(): String {
    val stringWriter = StringWriter()
    this.printStackTrace(PrintWriter(stringWriter))
    return stringWriter.toString()
}

fun readString(stream: InputStream): String {
    val sb = StringBuilder()
    val result = CharArray(65 * 1024)
    InputStreamReader(stream).use {
        var read: Int
        while (true) {
            read = it.read(result)
            if (read > 0)
                sb.append(result, 0, read)
            else
                break
        }
    }
    return sb.toString()
}

fun <T> Iterable<T>.isEmpty() = !this.any()
