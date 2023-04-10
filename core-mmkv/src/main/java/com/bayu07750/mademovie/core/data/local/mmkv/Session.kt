package com.bayu07750.mademovie.core.data.local.mmkv

import com.tencent.mmkv.MMKV

class Session(
    private val mmkv: MMKV
) {

    fun setValue(key: String, value: String) = mmkv.encode(key, value)

    fun getString(key: String) = mmkv.decodeString(key).orEmpty()

    companion object {
        const val KEY_LANGUAGE = "language"
    }
}