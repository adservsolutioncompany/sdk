package com.adservsolution.sdk.domain.model.request

import com.adservsolution.sdk.domain.model.Event

data class SendEventRequest(
    var advToken: String,
    var appName: String,
    var pixelId: String,
    var userId: String,
    var event: Event
)
