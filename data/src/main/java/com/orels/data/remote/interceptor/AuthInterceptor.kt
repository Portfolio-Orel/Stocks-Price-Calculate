package com.orels.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .addHeader("X-RapidAPI-Key", "d5521624a8msh964b295244bd92bp1b86e0jsn6dee14943944")
            .addHeader("X-RapidAPI-Host", "yh-finance.p.rapidapi.com")
            .build()
            .run { chain.proceed(this) }
    }
}