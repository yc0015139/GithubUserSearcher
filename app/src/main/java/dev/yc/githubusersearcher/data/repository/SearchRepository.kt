package dev.yc.githubusersearcher.data.repository

import androidx.paging.PagingData
import dev.yc.githubusersearcher.model.user.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchGithubUsers(
        keyword: String
    ): Flow<PagingData<User>>
}