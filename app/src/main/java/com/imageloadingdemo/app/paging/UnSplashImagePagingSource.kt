package com.imageloadingdemo.app.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.imageloadingdemo.app.model.UnSplashImageDataItem
import com.imageloadingdemo.app.repository.ProductRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UnSplashImagePagingSource @Inject constructor(
    private val repository: ProductRepository
) : PagingSource<Int, UnSplashImageDataItem>() {

    override fun getRefreshKey(state: PagingState<Int, UnSplashImageDataItem>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashImageDataItem> {
        val page = params.key ?: 1
        val hashMap = mutableMapOf<String, Any>()
        hashMap["client_id"] = "7gVXEuJRHsHyrmuHDxYGxkgotKJH-Ec8XZ9qH_lS-E0"
        hashMap["page"] = page

        val response = repository.loadImage(hashMap)
        return try {
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                e
            )
        }
    }
}