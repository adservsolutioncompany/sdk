package com.adservsolution.sdk.domain.model

import com.google.gson.JsonObject
import java.util.Locale

class Event(
    val key: Any,
    private val value: EventValue
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    override fun toString(): String {
        val json = JsonObject()

        val jsonValue = JsonObject()
        jsonValue.addProperty("id", value.id)
        jsonValue.addProperty("aud", value.pixelId)

        json.add(key.toString().toLowerCase(Locale.getDefault()), jsonValue)

        return json.toString()
    }
}
