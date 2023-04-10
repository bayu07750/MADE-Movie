package com.bayu07750.mademovie.core.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bayu07750.mademovie.core.data.network.model.MovieResponse
import com.bayu07750.mademovie.core.data.network.model.MoviesResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.messageOrNull

class MoviePagingSource(
    private val source: suspend (page: Int) -> ApiResponse<MoviesResponse>
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: 1
        return when (val response = source.invoke(page)) {
            is ApiResponse.Failure.Error -> {
                LoadResult.Error(throwable = Throwable(response.messageOrNull))
            }
            is ApiResponse.Failure.Exception -> {
                LoadResult.Error(throwable = Throwable(response.messageOrNull))
            }
            is ApiResponse.Success -> {
                val data = response.data.results ?: emptyList()
                val nextKey = if (data.size < params.loadSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey,
                )
            }
        }
    }
}