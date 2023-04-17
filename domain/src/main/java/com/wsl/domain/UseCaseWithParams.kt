package com.wsl.domain

import com.wsl.utils.Failure
import com.wsl.utils.Response

interface UseCaseWithParams<T, Params> {

    suspend operator fun invoke(params: Params): Response<Failure, T>

}