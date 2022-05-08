package com.app.teambrite.api

import com.app.teambrite.model.Covid19DataModel
import retrofit2.Call
import retrofit2.http.GET

interface TaskServiceAPI {

    @GET("/data.json")
    fun getCovid19Data(): Call<Covid19DataModel>
}