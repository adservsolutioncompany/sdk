package com.adservsolution.sdk

import android.content.Context
import com.adservsolution.sdk.di.startKoin
import com.adservsolution.sdk.domain.model.Constant
import com.adservsolution.sdk.utils.ConstantsNotProvidedException
import com.adservsolution.sdk.utils.DomainNotProvidedException

class AdservsolutionSdk private constructor(
    private val sdkManager: SdkManager
) : AdservsolutionAction {

    override fun prepareEvents(events: List<Any>) {
        sdkManager.prepareEvents(events)
    }

    override fun sendEvent(event: Any) {
        sdkManager.sendEvent(event)
    }

    class Builder(private val context: Context) {

        private var constant: Constant = Constant()

        fun domain(
            domain: String
        ) = apply {
            constant.domain = domain
        }

        fun advToken(
            advToken: String
        ) = apply {
            constant.advToken = advToken
        }

        fun appName(
            appName: String
        ) = apply {
            constant.appName = appName
        }

        fun pixelId(
            pixelId: String
        ) = apply {
            constant.pixelId = pixelId
        }

        fun build(): AdservsolutionSdk {
            if (constant.domain == null) {
                throw DomainNotProvidedException()
            }

            if (constant.invalid()) {
                throw ConstantsNotProvidedException()
            }

            startKoin(
                context,
                constant.domain!!
            )

            sdk = AdservsolutionSdk(
                SdkManager(
                    context,
                    constant
                )
            )

            return sdk!!
        }
    }

    companion object {

        var sdk: AdservsolutionSdk? = null

        val instance: AdservsolutionSdk
            get() {
                return sdk!!
            }
    }
}
