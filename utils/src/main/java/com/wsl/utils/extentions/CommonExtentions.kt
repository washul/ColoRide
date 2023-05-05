package com.wsl.utils.extensions

fun Float.hasMidStar(): Boolean {
    return this.rem(1).toDouble() % 100 >= 0.5
}



