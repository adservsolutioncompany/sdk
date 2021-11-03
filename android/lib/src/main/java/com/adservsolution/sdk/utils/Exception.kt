package com.adservsolution.sdk.utils

class DomainNotProvidedException : Exception("Missing domain.")

class ConstantsNotProvidedException : Exception("Missing constants [advToken, appName, eventId, pixelId].")
