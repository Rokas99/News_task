package eurofondas.news_task.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.repositories.NewsRepository

class NewsViewModel : ViewModel() {

    var newsResultMutableData : MutableLiveData<GetNewsResponse> = MutableLiveData()

    fun getNews()
    {
        NewsRepository().getNews(object : NewsRepository.INewsResponse {

            override fun OnResponse(getNewsResponse: GetNewsResponse?) {
                newsResultMutableData.postValue(getNewsResponse)
            }

            override fun OnFailure(t: Throwable) {
                Log.d("FAILURE", t.toString())
            }

        })
    }

    fun getNewsResult() : LiveData<GetNewsResponse>
    {
        return newsResultMutableData
    }
}