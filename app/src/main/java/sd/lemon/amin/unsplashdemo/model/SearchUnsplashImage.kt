package sd.lemon.amin.unsplashdemo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUnsplashImage(
    @SerialName("results")
    val images: List<UnsplashImage>
)