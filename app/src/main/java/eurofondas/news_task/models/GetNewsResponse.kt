package eurofondas.news_task.models

data class GetNewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)