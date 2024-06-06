package com.dino.kakaosearchexample.ui.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseKakaoSearchResponse<T>(
    val meta: Meta,
    val documents: List<T>,
) {

    @Serializable
    data class Meta(
        @SerialName("total_count")
        val totalCount: Int,
        @SerialName("pageable_count")
        val pageableCount: Int,
        @SerialName("is_end")
        val isEnd: Boolean,
    )
}