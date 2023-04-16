package eurofondas.news_task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eurofondas.news_task.models.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun roomDao() : ArticleDao

    companion object {
        @Volatile
        var INSTANCE : ArticleDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : ArticleDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context,
                    ArticleDatabase::class.java,
                "room.db").fallbackToDestructiveMigration().build()
            }

            return INSTANCE as ArticleDatabase
        }
    }
}