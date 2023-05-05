package com.wsl.data

import com.google.gson.GsonBuilder
import com.wsl.data.city.apiservice.CityApiService
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://api.geonames.org/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
}

fun provideCityApiService(retrofit: Retrofit): CityApiService {
    return retrofit.create(CityApiService::class.java)
}

fun providePlainOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient().newBuilder()
        .cookieJar(SessionCookieJar())
        .addInterceptor(provideTokensInterceptor())
    okHttpClientBuilder.networkInterceptors()

    return okHttpClientBuilder.build()
}

fun provideTokensInterceptor(): Interceptor =
    Interceptor { chain ->

        val original: Request = chain.request()
        val originalURL: HttpUrl = original.url()

        val usurperURL: HttpUrl = originalURL.newBuilder()
            .addQueryParameter("country", "mx")
            .addQueryParameter("cities", "cities1000")
            .addQueryParameter("username", "coloride")
            .build()

        val usurperRequest = original.newBuilder()
            .url(usurperURL)
            .build()

        chain.proceed(usurperRequest)
    }

fun provideCookieJar(): CookieJar = SessionCookieJar()

private class SessionCookieJar : CookieJar {

    private var cookies: List<Cookie>? = null

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.encodedPath().endsWith("login")) {
            this.cookies = ArrayList(cookies)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return if (!url.encodedPath().endsWith("login") && cookies != null) {
            this.cookies!!
        } else listOf<Cookie>()
    }
}
