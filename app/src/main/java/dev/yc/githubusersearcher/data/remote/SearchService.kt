package dev.yc.githubusersearcher.data.remote

import dev.yc.githubusersearcher.model.user.UserItems
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") keyword: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int,
    ): UserItems
}