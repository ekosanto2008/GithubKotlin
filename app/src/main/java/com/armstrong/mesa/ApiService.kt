package com.armstrong.mesa

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{username}")
    fun getUsers(@Path("username") user: String): Call<User>

    @GET("search/users")
    fun getData(@Query("q") query: String): Call<UserSearch>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") user: String): Call<List<UserSearch.Result>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") user: String): Call<List<UserSearch.Result>>
}