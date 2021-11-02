package com.adservsolution.sdk

interface AdservsolutionAction {

    fun prepareEvents(events: List<Any>)

    fun sendEvent(event: Any)
}
