package utils

import java.util.*

fun <T, TKey, TValue> Iterable<T>.toMap(keyFunc: (T) -> TKey, valueFunc: (T) -> TValue): HashMap<TKey, TValue> {
    val result = HashMap<TKey, TValue>()
    for(el in this) result.put(keyFunc(el), valueFunc(el))
    return result

}

