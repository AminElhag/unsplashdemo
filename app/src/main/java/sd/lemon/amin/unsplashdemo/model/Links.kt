package sd.lemon.amin.unsplashdemo.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @SerializedName("html") val html: String
)
