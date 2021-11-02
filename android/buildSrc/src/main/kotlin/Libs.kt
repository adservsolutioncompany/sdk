object Android {
    const val core_ktx: String =
        "androidx.core:core-ktx:${Versions.android_core_ktx}"

    const val appcompat: String =
        "androidx.appcompat:appcompat:${Versions.android_appcompat}"

    const val material: String =
        "com.google.android.material:material:${Versions.android_material}"

    const val constraintlayout: String =
        "androidx.constraintlayout:constraintlayout:${Versions.android_constraintlayout}"

    const val lifecycle_extensions: String =
        "androidx.lifecycle:lifecycle-extensions:${Versions.android_lifecycle_extensions}"

    const val lifecycle_viewmodel: String =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.android_lifecycle_viewmodel}"

    const val lifecycle_livedata: String =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.android_lifecycle_livedata}"

    const val gson: String =
        "com.google.code.gson:gson:${Versions.android_gson}"

    const val coroutines: String =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.android_coroutines}"

    const val room_compiler: String =
        "androidx.room:room-compiler:${Versions.android_room}"

    const val room_runtime: String =
        "androidx.room:room-runtime:${Versions.android_room}"

    const val room_ktx: String =
        "androidx.room:room-ktx:${Versions.android_room}"
}

object Kotlin {
    const val stdlib_jdk7: String =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib_jdk7}"

    const val coroutine_core: String =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutine_core}"
}

object Koin {
    const val android: String =
        "io.insert-koin:koin-android:${Versions.koin}"

    const val android_compat: String =
        "io.insert-koin:koin-android-compat:${Versions.koin}"
}

object Squareup {
    const val retrofit2: String =
        "com.squareup.retrofit2:retrofit:${Versions.squareup_retrofit2}"

    const val retrofit2_coroutines: String =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.squareup_retrofit2_coroutines}"

    const val retrofit2_converter: String =
        "com.squareup.retrofit2:converter-gson:${Versions.squareup_retrofit2}"

    const val okhttp3_logging: String =
        "com.squareup.okhttp3:logging-interceptor:${Versions.squareup_okhttp3_logging}"
}
