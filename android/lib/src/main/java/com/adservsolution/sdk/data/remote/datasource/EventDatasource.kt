package com.adservsolution.sdk.data.remote.datasource

import com.adservsolution.sdk.data.remote.service.EventService
import com.adservsolution.sdk.domain.model.request.SendEventRequest

class EventDatasource(
    private val eventService: EventService
) {

    fun sendEventAsync(request: SendEventRequest) =
        eventService.sendEventAsync(
            request.advToken,
            request.appName,
            request.event,
            request.pixelId,
            request.userId
        )
}
