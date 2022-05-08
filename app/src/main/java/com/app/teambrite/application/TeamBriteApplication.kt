package com.app.teambrite.application

import android.app.Application
import android.content.Context
import com.app.teambrite.api.TaskServiceAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TeamBriteApplication : Application() {

    companion object {
        var ctx: Context? = null
        private var taskServiceAPI: TaskServiceAPI? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        initLoginApi()
    }

    private fun initLoginApi() {
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setLenient()
            .create()
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://data.covid19india.org")
            .client(okHttpClient)
            .build()
        taskServiceAPI = retrofit.create(TaskServiceAPI::class.java)
    }

    fun getApis(): TaskServiceAPI? {
        return taskServiceAPI
    }

}