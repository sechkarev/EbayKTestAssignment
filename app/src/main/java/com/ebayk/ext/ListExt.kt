package com.ebayk.ext

fun<T> List<T>.divideToPairs(): List<Pair<T, T?>> {
    val result = mutableListOf<Pair<T, T?>>()
    for (index in 0 until size / 2) {
        result.add(this[index * 2] to this[index * 2 + 1])
    }
    if (size % 2 != 0) {
        result.add(last() to null)
    }
    return result
}