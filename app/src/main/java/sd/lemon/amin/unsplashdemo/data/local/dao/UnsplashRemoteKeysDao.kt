package sd.lemon.amin.unsplashdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sd.lemon.amin.unsplashdemo.model.UnsplashRemoteKeys

@Dao
interface UnsplashRemoteKeysDao {
    @Query("SELECT * FROM unsplash_image_table WHERE id = :id")
    fun getUnsplashRemoteKeyImage(id: String): UnsplashRemoteKeys

    @Query("DELETE FROM unsplash_remote_keys_table")
    fun deleteAllKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addKeys(keys: List<UnsplashRemoteKeys>)
}