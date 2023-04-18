package eurofondas.news_task.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eurofondas.news_task.db.ArticleDatabase
import eurofondas.news_task.models.Article
import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.repositories.NewsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val articleDatabase : ArticleDatabase,
                                        private val newsRepository: NewsRepository) : ViewModel() {

    private var newsResultMutableData: MutableLiveData<GetNewsResponse?> = MutableLiveData()
    private var savedResultMutableData: MutableLiveData<List<Article>> = MutableLiveData()
    private var errorsResultMutableData: MutableLiveData<String> = MutableLiveData()

    fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews(object : NewsRepository.INewsResponse {

                override fun onResponse(getNewsResponse: GetNewsResponse?) {
                    newsResultMutableData.postValue(getNewsResponse)
                }

                override fun onFailure(t: Throwable) {
                    errorsResultMutableData.postValue(t.message)
                }

            })
        }
    }

    fun getNewsResult(): MutableLiveData<GetNewsResponse?> {
        return newsResultMutableData
    }

    fun getSavedArticlesResult(): LiveData<List<Article>> {
        return savedResultMutableData
    }

    fun getErrorsResult(): LiveData<String> {
        return errorsResultMutableData
    }

    fun insertArticle(article: Article)
    {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->

            errorsResultMutableData.postValue("Failed to save article")
        }) {
            articleDatabase.articleDao().InsertArticle(article)
        }

    }

    fun getSavedNews()
    {
        viewModelScope.launch { 
            savedResultMutableData.postValue(articleDatabase.articleDao().getArticles())
        }
    }
}