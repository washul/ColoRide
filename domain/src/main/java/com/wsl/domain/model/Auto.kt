package com.wsl.domain.model

import android.net.Uri

data class Auto(
    val name: String = "",
    val image: Uri? = null,
    val seatNumber: Int = 5
)
