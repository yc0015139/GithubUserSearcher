package dev.yc.githubusersearcher.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.yc.githubusersearcher.PAGING_FIRST_PAGE
import dev.yc.githubusersearcher.data.remote.SearchService
import dev.yc.githubusersearcher.model.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val dispatcher: CoroutineDispatcher,
    private val searchService: SearchService,
    private val query: String,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            withContext(dispatcher) {
                val pageNumber = params.key ?: PAGING_FIRST_PAGE
                val result = searchService.searchUser(query, params.loadSize, pageNumber)
                LoadResult.Page(
                    data = result.items,
                    prevKey = null,
                    nextKey = pageNumber.inc()
                )
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.inc() ?: anchorPage?.nextKey?.dec()
        }
}