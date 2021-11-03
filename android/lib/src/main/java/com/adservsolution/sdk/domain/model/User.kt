package com.adservsolution.sdk.domain.model

data class User(
    val userId: String,
    val email: String? = null,
    val fullName: String? = null
)
