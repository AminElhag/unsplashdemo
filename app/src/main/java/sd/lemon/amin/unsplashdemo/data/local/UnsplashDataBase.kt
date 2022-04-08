package sd.lemon.amin.unsplashdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import sd.lemon.amin.unsplashdemo.data.local.dao.UnsplashImageDao
import sd.lemon.amin.unsplashdemo.data.local.dao.UnsplashRemoteKeysDao
import sd.lemon.amin.unsplashdemo.model.UnsplashImage
import sd.lemon.amin.unsplashdemo.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 2)
abstract class UnsplashDataBase : RoomDatabase() {
    abstract fun unsplashImageDeo(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}