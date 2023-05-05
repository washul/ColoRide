package com.wsl.data.city.apiservice

import com.wsl.domain.model.MainCityApiResponseObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CityApiService {

    @GET("searchJSON")
    suspend fun searchCity(@Query("name_startsWith") name: String): Response<MainCityApiResponseObject>

}