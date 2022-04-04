package sd.lemon.amin.unsplashdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import sd.lemon.amin.unsplashdemo.util.Constants.UNSPLASH_REMOTE_KEYS_TABLE_NAME

@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE_NAME)
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val pervPage: Int?,
    val nextPage: Int?
)
