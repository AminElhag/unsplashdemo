package sd.lemon.amin.unsplashdemo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sd.lemon.amin.unsplashdemo.data.local.UnsplashDataBase
import sd.lemon.amin.unsplashdemo.data.paging.SearchPagingSource
import sd.lemon.amin.unsplashdemo.data.paging.UnsplashRemoteMediator
import sd.lemon.amin.unsplashdemo.data.remote.UnsplashAPI
import sd.lemon.amin.unsplashdemo.model.UnsplashImage
import sd.lemon.amin.unsplashdemo.util.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashAPI: UnsplashAPI,
    private val unsplashDataBase: UnsplashDataBase
) {

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDataBase.unsplashImageDeo().getAllImage() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashAPI = unsplashAPI,
                unsplashDataBase = unsplashDataBase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashAPI, query = query)
            }
        ).flow
    }
}