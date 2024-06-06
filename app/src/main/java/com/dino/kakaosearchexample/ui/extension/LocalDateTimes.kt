package com.dino.kakaosearchexample.ui.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val simpleDatePattern = DateTimeFormatter.ofPattern("yyyy.MM.dd")
fun LocalDateTime.toSimpleDateFormat(): String {
    return this.format(simpleDatePattern)
}