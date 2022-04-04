package sd.lemon.amin.unsplashdemo.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import sd.lemon.amin.unsplashdemo.BuildConfig
import sd.lemon.amin.unsplashdemo.model.UnsplashImage

interface UnsplashAPI {

    //https://api.unsplash.com/photos?per_page=2
    @Headers("Authorization : Client-ID ${BuildConfig.APPLICATION_ID}")
    @GET("/photos")
    suspend fun getAllImage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UnsplashImage>

    //GET /search/photos
    @Headers("Authorization : Client-ID ${BuildConfig.APPLICATION_ID}")
    @GET("/search/photos")
    suspend fun getSearchImage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UnsplashImage>
}