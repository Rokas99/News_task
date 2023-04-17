package eurofondas.news_task.repositories

import android.util.Log
import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.retrofit.Connection
import eurofondas.news_task.retrofit.NewsService
import java.io.IOException

class NewsRepository {

    suspend fun getNews(newsResponse: INewsResponse) {
        val newsService: NewsService?

        try {
            newsService = Connection.getRetrofit()?.create(NewsService::class.java)
        } catch (exception: IOException) {
            Log.d("FAILED", exception.message.toString())
            newsResponse.onFailure(Throwable("Connection error"))
            return
        }

        val request = try {
            newsService?.getNews()
        } catch (exception: IOException) {
            Log.d("FAILED", exception.message.toString())
            newsResponse.onFailure(Throwable("Connection error"))
            return
        }

        if (request != null) {
            if (request.isSuccessful) {
                newsResponse.onResponse(request.body())
            } else {
                Log.d("FAILED", request.message() ?: "")
                newsResponse.onFailure(Throwable(request.message()))
            }
        } else {
            newsResponse.onFailure(Throwable("Request is empty"))
        }
    }


    interface INewsResponse {
        fun onResponse(getNewsResponse: GetNewsResponse?);
        fun onFailure(t: Throwable);
    }
}