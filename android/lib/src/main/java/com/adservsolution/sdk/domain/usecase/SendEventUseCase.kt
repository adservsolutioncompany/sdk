package com.adservsolution.sdk.domain.usecase

import com.adservsolution.sdk.domain.model.request.SendEventRequest
import com.adservsolution.sdk.domain.repository.EventRepository

class SendEventUseCase(
    private val repository: EventRepository
) {

    suspend operator fun invoke(
        request: SendEventRequest
    ) = repository.sendEvent(request)
}
