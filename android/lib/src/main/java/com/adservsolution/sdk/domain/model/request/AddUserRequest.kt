package com.adservsolution.sdk.domain.model.request

data class AddUserRequest(
    var os: String,
    var osv: String,
    var make: String,
    var model: String,
    var hwv: String = model,
    var sr: String,
    var hc: Int,
    var mem: Int,
    var ln: String,
    var tz: Int,
    var did: String
)
