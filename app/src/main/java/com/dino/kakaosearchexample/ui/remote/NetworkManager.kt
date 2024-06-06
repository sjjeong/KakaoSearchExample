package com.dino.kakaosearchexample.ui.remote

import com.dino.kakaosearchexample.ui.remote.service.KakaoSearchApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object NetworkManager {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private const val kakaoApiUrl = "https://dapi.kakao.com"

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))

    private val kakaoApiRetrofit = retrofitBuilder.baseUrl(kakaoApiUrl)
        .build()

    val kakaoSearchApi = kakaoApiRetrofit.create<KakaoSearchApi>()

}