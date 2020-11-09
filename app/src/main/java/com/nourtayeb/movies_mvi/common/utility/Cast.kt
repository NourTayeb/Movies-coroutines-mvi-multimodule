package com.nourtayeb.movies_mvi.common.utility

inline fun <reified T> Any?.castDo(block: T.() -> Unit) {
    if (this is T) {
        block()
    }
}
inline fun <reified T> Any?.castReturn(): T? {
    if (this is T) {
        return this
    }
    return null
}
inline fun <reified T> Any?.instanceOf() = (this is T)

