package com.cornerweather.android.logic.network

import com.google.gson.GsonBuilder

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    /**
     * 彩云返回的日期是 ISO 8601 带时区的格式，例如 2026-11-09T00:00+08:00。
     * Gson 默认的 Date 解析器只认本地化格式，必须显式指定。
     */
    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mmXXX")
        .create()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            retrofit2.converter.gson.GsonConverterFactory.create(gson)
        )
        .build()
    fun<T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline  fun <reified T> create(): T = create(T::class.java)
}