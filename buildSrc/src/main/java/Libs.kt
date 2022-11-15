object Libs {

    // Android base
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val material = "com.google.android.material:material:${Versions.material_version}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout_version}"
    const val startup = "androidx.startup:startup-runtime:${Versions.startup_version}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation_version}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragment_version}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment_version}"

    // Kotlin
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine_version}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine_version}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization_version}"

    // Lifecycle
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"

    // Koin
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin_version}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin_version}"
    const val koinScope = "io.insert-koin:koin-androidx-scope:${Versions.koin_version}"
    const val koinViewModel = "io.insert-koin:koin-androidx-viewmodel:${Versions.koin_version}"

    // OkHttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"

    // Gson
    const val gson = "com.google.code.gson:gson:${Versions.gson_version}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room_version}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.room_version}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room_version}"

    // DataStore preferences
    const val datastorePreferences = "androidx.datastore:datastore-preferences:${Versions.datastore_version}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"
}
