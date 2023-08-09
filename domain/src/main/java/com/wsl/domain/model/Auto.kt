package com.wsl.domain.model

import android.net.Uri

data class Auto(
    var uuid: String = "",
    var name: String = "",
    val image: Uri? = null,
    var seatNumber: Int = 5,
    var isSelected: Boolean = false
) {
    fun isReadyToSave(): Boolean {
        return name.isNotBlank() || name.isNotEmpty() && seatNumber > 0
    }

    fun showAutoTitle(): String = "$name with $seatNumber people capacity"
}

data class AutoJsonList(
    val list: List<Auto>
)