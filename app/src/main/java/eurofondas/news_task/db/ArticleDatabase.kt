package eurofondas.news_task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eurofondas.news_task.models.Article
import javax.inject.Inject
import javax.inject.Singleton

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun articleDao() : ArticleDao
}