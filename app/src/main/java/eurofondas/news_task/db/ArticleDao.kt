package eurofondas.news_task.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import eurofondas.news_task.models.Article

@Dao
interface ArticleDao {

    @Insert
    suspend fun InsertArticle(article : Article)

    @Query("SELECT * FROM article")
    suspend fun getArticles() : List<Article>
}