package com.wsl.domain

import com.wsl.utils.Failure
import com.wsl.utils.Result

interface UseCase<T> {
    suspend operator fun invoke(): Result<Failure, T>
}