package sd.lemon.amin.unsplashdemo.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sd.lemon.amin.unsplashdemo.model.UnsplashImage

@Dao
interface UnsplashImageDao {
    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImage(): PagingSource<Int, UnsplashImage>

    @Query("DELETE FROM unsplash_image_table")
    fun deleteAllImage()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImages(image: List<UnsplashImage>)
}