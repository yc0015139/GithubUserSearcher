package dev.yc.githubusersearcher.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.yc.githubusersearcher.PAGING_SIZE
import dev.yc.githubusersearcher.data.paging.UserPagingSource
import dev.yc.githubusersearcher.data.remote.SearchService
import dev.yc.githubusersearcher.data.remote.ServiceGenerator
import dev.yc.githubusersearcher.model.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val searchService: SearchService = ServiceGenerator.createService(SearchService::class.java),
) : SearchRepository {
    override suspend fun searchGithubUsers(
        keyword: String
    ): Flow<PagingData<User>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                UserPagingSource(
                    dispatcher,
                    searchService,
                    query = keyword
                )
            }
        ).flow.flowOn(dispatcher)
}