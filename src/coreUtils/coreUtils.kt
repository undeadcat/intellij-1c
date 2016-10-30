@file:JvmName("CoreUtils")

package coreUtils

import java.util.*

fun <T, TKey, TValue> Iterable<T>.toMap(keyFunc: (T) -> TKey, valueFunc: (T) -> TValue): HashMap<TKey, TValue> {
    val result = HashMap<TKey, TValue>()
    for (el in this) result.put(keyFunc(el), valueFunc(el))
    return result
}

fun <T> Iterable<T>.isEquivalentTo(other: Iterable<T>): Boolean {
    val set = this.toHashSet()
    for (item in other) {
        if (!set.remove(item))
            return false
    }
    return set.isEmpty()
}

fun parseIntOrNull(string: String): Int? {
    try {
        return Integer.parseInt(string)
    } catch (e: NumberFormatException) {
        return null
    }
}

