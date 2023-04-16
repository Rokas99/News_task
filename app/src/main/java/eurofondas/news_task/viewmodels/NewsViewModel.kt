package eurofondas.news_task.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eurofondas.news_task.db.ArticleDatabase
import eurofondas.news_task.models.Article
import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(val articleDatabase : ArticleDatabase) : ViewModel() {

    var newsResultMutableData: MutableLiveData<GetNewsResponse> = MutableLiveData()
    var savedResultMutableData: MutableLiveData<List<Article>> = MutableLiveData()

    fun getNews() {
        viewModelScope.launch {
            NewsRepository().getNews(object : NewsRepository.INewsResponse {

                override fun OnResponse(getNewsResponse: GetNewsResponse?) {
                    newsResultMutableData.postValue(getNewsResponse)
                }

                override fun OnFailure(t: Throwable) {
                    Log.d("FAILURE", t.toString())
                }

            })
        }
    }

    fun getNewsResult(): LiveData<GetNewsResponse> {
        return newsResultMutableData
    }

    fun getSavedArticlesResult(): LiveData<List<Article>> {
        return savedResultMutableData
    }

    fun insertArticle(article: Article)
    {
        Log.d("ARTICLE", "CALLED")
        viewModelScope.launch {
            articleDatabase.roomDao().InsertArticle(article)
        }
    }

    fun getSavedNews()
    {
        viewModelScope.launch { 
            savedResultMutableData.postValue(articleDatabase.roomDao().getArticles())
        }
    }
}