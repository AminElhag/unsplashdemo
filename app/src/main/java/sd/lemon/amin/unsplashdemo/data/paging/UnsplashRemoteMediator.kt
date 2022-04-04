package sd.lemon.amin.unsplashdemo.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import sd.lemon.amin.unsplashdemo.data.local.UnsplashDataBase
import sd.lemon.amin.unsplashdemo.data.remote.UnsplashAPI
import sd.lemon.amin.unsplashdemo.model.UnsplashImage
import sd.lemon.amin.unsplashdemo.model.UnsplashRemoteKeys
import sd.lemon.amin.unsplashdemo.util.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

@ExperimentalPagingApi
class UnsplashRemoteMediator @Inject constructor(
    private val unsplashAPI: UnsplashAPI,
    private val unsplashDataBase: UnsplashDataBase
) : RemoteMediator<Int, UnsplashImage>() {

    private val unsplashImageDeo = unsplashDataBase.unsplashImageDeo()
    private val unsplashRemoteKeysDao = unsplashDataBase.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteClosesToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.pervPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = unsplashAPI.getAllImage(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            unsplashDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDeo.deleteAllImage()
                    unsplashRemoteKeysDao.deleteAllKeys()
                }
                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        pervPage = prevPage,
                        nextPage = nextPage
                    )
                }
                unsplashRemoteKeysDao.addKeys(keys = keys)
                unsplashImageDeo.addImages(image = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getUnsplashRemoteKeyImage(id = unsplashImage.id)
            }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getUnsplashRemoteKeyImage(id = unsplashImage.id)
            }
    }

    private fun getRemoteClosesToCurrentPosition(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getUnsplashRemoteKeyImage(id = id)
            }
        }
    }
}