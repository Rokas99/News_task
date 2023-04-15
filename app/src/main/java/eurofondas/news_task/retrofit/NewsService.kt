package eurofondas.news_task.retrofit

import eurofondas.news_task.models.GetNewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("/v2/top-headlines?country=us")
    fun getNews(): Call<GetNewsResponse>
}