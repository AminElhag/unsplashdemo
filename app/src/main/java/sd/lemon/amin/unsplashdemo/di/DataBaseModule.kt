package sd.lemon.amin.unsplashdemo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sd.lemon.amin.unsplashdemo.data.local.UnsplashDataBase
import sd.lemon.amin.unsplashdemo.util.Constants.UNSPLASH_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): UnsplashDataBase {
        return Room.databaseBuilder(
            context,
            UnsplashDataBase::class.java,
            UNSPLASH_DATABASE
        ).build()
    }
}