package com.wsl.domain.model

data class UserRating(
    val userRating: Float = 0.0f,
    val asOwner: Float = 0.0f,
    val asPassenger: Float = 0.0f
)
