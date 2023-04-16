package eurofondas.news_task.logic

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Logic {

    fun formatDate(dateString : String) : String
    {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = dateFormat.parse(dateString)

        val dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())

        return dateFormatter.format(date)
    }

}