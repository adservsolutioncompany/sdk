package com.adservsolution.sdk.domain.model

data class Constant(
    var domain: String? = null,
    var advToken: String? = null,
    var appName: String? = null,
    var pixelId: String? = null
) {

    fun invalid() = advToken == null || appName == null || pixelId == null

    override fun toString(): String {
        return "Constant(domain=$domain, advToken=$advToken, appName=$appName, pixelId=$pixelId"
    }
}
