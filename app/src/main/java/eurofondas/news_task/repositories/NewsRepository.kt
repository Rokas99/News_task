package eurofondas.news_task.repositories

import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.retrofit.Connection
import eurofondas.news_task.retrofit.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    suspend fun getNews(newsResponse: INewsResponse) {
        val newsService: NewsService? = Connection.getRetrofit()?.create(NewsService::class.java)

        val request = newsService?.getNews()

        if (request != null) {
            if (request.isSuccessful)
                newsResponse.OnResponse(request.body())
            else
                newsResponse.OnFailure(Throwable(request.message()))
        }

        newsResponse.OnFailure(Throwable(request?.message()))
    }

    interface INewsResponse {
        fun OnResponse(getNewsResponse: GetNewsResponse?);
        fun OnFailure(t: Throwable);
    }
}