package eurofondas.news_task.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eurofondas.news_task.db.ArticleDatabase

class ArticleViewModelFactory(private val articleDatabase: ArticleDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(articleDatabase) as T
    }
}