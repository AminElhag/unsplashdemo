package sd.lemon.amin.unsplashdemo.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import sd.lemon.amin.unsplashdemo.util.Constants.UNSPLASH_IMAGE_TABLE_NAME

@Serializable
@Entity(tableName = UNSPLASH_IMAGE_TABLE_NAME)
data class UnsplashImage(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: String,
    @Embedded
    @SerializedName("urls") val urls: Urls,
    @Embedded
    @SerializedName("user") val user: User,
    @SerializedName("likes") val likes: Int,
)
