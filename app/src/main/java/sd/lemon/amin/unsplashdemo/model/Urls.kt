package sd.lemon.amin.unsplashdemo.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    @SerializedName("regular") val regular: String
)
