package com.adservsolution.sdk.domain.repository

import com.adservsolution.sdk.domain.model.request.SendEventRequest

interface EventRepository {

    suspend fun sendEvent(
        request: SendEventRequest
    ): Void?
}
