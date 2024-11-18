package com.example.musicx

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers(
        "X-RapidAPI-Key: bd31489d16msh10d6f33385fc6b8p1e388fjsne09231a90415",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )

    @GET("search")
    fun getData(@Query("q") query: String): Call<MyData>
}