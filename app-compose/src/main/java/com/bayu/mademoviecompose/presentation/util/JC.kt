package com.bayu.mademoviecompose.presentation.util

typealias JCallback = () -> Unit
typealias JCallbackBoolean = (value: Boolean) -> Unit
typealias JCallbackInt = (value: Int) -> Unit
typealias JCallbackFloat = (value: Int) -> Unit
typealias JCallbackString = (value: String) -> Unit
typealias JCallbackChar = (value: Char) -> Unit

typealias JCallbackType<T> = (value: T) -> Unit
typealias JCallbackType2<T, U> = (value1: T, value2: U) -> Unit
typealias JCallbackType3<T, U, V> = (value1: T, value2: U, value3: V) -> Unit
typealias JCallbackType4<T, U, V, W> = (value1: T, value2: U, value3: V, value4: W) -> Unit
typealias JCallbackType5<T, U, V, W, X> = (value1: T, value2: U, value3: V, value4: W, value5: X) -> Unit
typealias JCallbackType6<T, U, V, W, X, Y> = (value1: T, value2: U, value3: V, value4: W, value5: X, value6: Y) -> Unit
typealias JCallbackType7<T, U, V, W, X, Y, Z> = (value1: T, value2: U, value3: V, value4: W, value5: X, value6: Y, value7: Z) -> Unit
typealias JCallbackType8<T, U, V, W, X, Y, Z, TT> = (value1: T, value2: U, value3: V, value4: W, value5: X, value6: Y, value7: Z, value8: TT) -> Unit
typealias JCallbackType9<T, U, V, W, X, Y, Z, TT, TU> = (value1: T, value2: U, value3: V, value4: W, value5: X, value6: Y, value7: Z, value8: TT, value9: TU) -> Unit
typealias JCallbackType10<T, U, V, W, X, Y, Z, TT, TU, TV> = (value1: T, value2: U, value3: V, value4: W, value5: X, value6: Y, value7: Z, value8: TT, value9: TU, value10: TV) -> Unit