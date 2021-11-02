package com.adservsolution.sdk.repository

import com.adservsolution.sdk.data.remote.datasource.EventDatasource
import com.adservsolution.sdk.domain.model.request.SendEventRequest
import com.adservsolution.sdk.domain.repository.EventRepository

class EventRepositoryImpl(
    private val eventDatasource: EventDatasource
) : EventRepository {

    override suspend fun sendEvent(
        request: SendEventRequest
    ): Void? {
        return safeApiCall {
            eventDatasource.sendEventAsync(request).await()
        }
    }
}
