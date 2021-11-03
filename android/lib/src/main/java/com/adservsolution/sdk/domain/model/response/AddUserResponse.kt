package com.adservsolution.sdk.domain.model.response

import com.google.gson.annotations.SerializedName

data class AddUserResponse(
    @SerializedName("token")
    var token: String? = null
)
