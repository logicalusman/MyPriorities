package com.mypriorities.data.network

import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MyPrioritiesNetworkAdapter {

    const val API_AUTH_KEY = "Bearer <Your API Key>"
    const val ADD_PRIORITY = "API/v8/tasks"
    const val DELETE_MY_PRIORITIES = "API/v8/tasks/{myPriorityId}"
    const val GET_MY_PRIORITIES = "/API/v8/tasks"
    const val BASE_URL = "https://beta.todoist.com"

    fun myPrioritiesApi(): MyPrioritiesApi {
        val builder = Builder()
        builder.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(builder.build())
                .build()

        return retrofit.create(MyPrioritiesApi::class.java)
    }


}