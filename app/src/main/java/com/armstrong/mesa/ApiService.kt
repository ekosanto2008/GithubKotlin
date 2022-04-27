package com.armstrong.mesa

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("search/users")
    fun getData(@Query("q") query: String): Call<UserSearch>
}