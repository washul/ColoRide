package com.wsl.domain.model

import android.net.Uri

data class User(
    val UUID: String,
    val userName: String,
    val email: String,
    val whatsApp: String,
    val image: Uri,
    val rating: UserRating = UserRating(),
    val userType: UserType = UserType.PASSENGER
)
