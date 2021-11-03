package com.adservsolution.sdk.utils

import com.adservsolution.sdk.domain.model.Constant
import com.adservsolution.sdk.domain.model.Event
import com.adservsolution.sdk.domain.model.EventValue
import com.adservsolution.sdk.domain.model.User
import com.adservsolution.sdk.domain.model.request.AddUserRequest
import com.adservsolution.sdk.domain.model.request.SendEventRequest

fun createUserRequest(device: DeviceUtils): AddUserRequest = with(device) {
    AddUserRequest(
        os = os(),
        osv = api(),
        make = manufacturer(),
        model = model(),
        hwv = device(),
        sr = screen(),
        hc = cpu(),
        mem = memory(),
        ln = language(),
        tz = timeZone(),
        did = id()
    )
}

fun createEventRequest(
    constant: Constant,
    user: User,
    event: Event
): SendEventRequest = with(constant) {
    SendEventRequest(
        advToken = advToken!!,
        appName = appName!!,
        pixelId = pixelId!!,
        userId = user.userId,
        event = event
    )
}

fun createEventValue(
    id: Long,
    constant: Constant
) = EventValue(
    id = id,
    pixelId = constant.pixelId
)
