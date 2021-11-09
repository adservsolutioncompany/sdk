package com.adservsolution.sdk

import android.content.Context
import android.util.Log
import com.adservsolution.sdk.data.remote.NetworkConnectionInterceptor
import com.adservsolution.sdk.domain.model.Constant
import com.adservsolution.sdk.domain.model.Event
import com.adservsolution.sdk.domain.model.User
import com.adservsolution.sdk.domain.usecase.AddUserUseCase
import com.adservsolution.sdk.domain.usecase.GetUserUseCase
import com.adservsolution.sdk.domain.usecase.SendEventUseCase
import com.adservsolution.sdk.utils.DeviceUtils
import com.adservsolution.sdk.utils.createEventRequest
import com.adservsolution.sdk.utils.createEventValue
import com.adservsolution.sdk.utils.createUserRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SdkManager(
    context: Context,
    var constant: Constant
) : KoinComponent {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val events = HashSet<Event>()
    private val device = DeviceUtils(context)

    private val addUserUseCase by inject<AddUserUseCase>()
    private val getUserUseCase by inject<GetUserUseCase>()
    private val sendEventUseCase by inject<SendEventUseCase>()

    private var user: User? = null

    init {
        addPredefinedEvents()
        checkUser()
    }

    fun sendEvent(event: Any) {
        events.find { it.key == event }?.let {
            uiScope.launch(Dispatchers.IO) {
                try {
                    sendEventUseCase(
                        createEventRequest(
                            constant,
                            user!!,
                            it
                        )
                    )
                    Log.d(TAG, "Event '$event' was sent.")
                } catch (e: NetworkConnectionInterceptor.NoConnectionException) {
                    Log.e(TAG, "NoConnectionException, call: sendEvent.")
                }
            }
        }
    }

    fun prepareEvents(events: List<Any>) {
        this.events.addAll(
            events.map {
                Event(
                    it,
                    createEventValue(
                        events.size.toLong(),
                        constant
                    )
                )
            }
        )
    }

    private fun checkUser() {
        uiScope.launch(Dispatchers.IO) {
            getUserUseCase().collect { user ->
                this@SdkManager.user = user

                Log.d(TAG, "User: ${user?.userId}")
                if (user == null) {
                    addUser()
                } else {
                    sendEvent(PredefinedEvent.OPEN)
                }
            }
        }
    }

    private suspend fun addUser() {
        try {
            addUserUseCase(
                createUserRequest(device),
                constant.advToken!!
            )
        } catch (e: NetworkConnectionInterceptor.NoConnectionException) {
            Log.e(TAG, "NoConnectionException, call: addUser.")
        }
    }

    private fun addPredefinedEvents() {
        events.add(
            Event(
                PredefinedEvent.OPEN,
                createEventValue(
                    events.size.toLong(),
                    constant
                )
            )
        )
    }

    companion object {

        private const val TAG = "SdkManager"
    }
}
