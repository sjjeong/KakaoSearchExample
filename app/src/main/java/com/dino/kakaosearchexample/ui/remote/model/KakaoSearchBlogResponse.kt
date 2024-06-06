package com.dino.kakaosearchexample.ui.remote.model

import com.dino.kakaosearchexample.ui.serializer.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class KakaoSearchBlogResponse(
    val title: String,
    val contents: String,
    val url: String,
    @SerialName("blogname")
    val blogName: String,
    val thumbnail: String,
    @SerialName("datetime")
    @Serializable(with = DateSerializer::class)
    val dateTime: LocalDateTime,
)
