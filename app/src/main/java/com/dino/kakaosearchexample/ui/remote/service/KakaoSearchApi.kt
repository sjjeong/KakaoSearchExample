package com.dino.kakaosearchexample.ui.remote.service

import com.dino.kakaosearchexample.BuildConfig
import com.dino.kakaosearchexample.ui.remote.model.BaseKakaoSearchResponse
import com.dino.kakaosearchexample.ui.remote.model.KakaoSearchBlogResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoSearchApi {

    @GET("/v2/search/blog")
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_REST_API_KEY}")
    suspend fun searchBlog(
        @Query("query") query: String,
        @Query("size") page: Int = 50, // @Range(from = 1, to = 50)
    ): BaseKakaoSearchResponse<KakaoSearchBlogResponse>
}