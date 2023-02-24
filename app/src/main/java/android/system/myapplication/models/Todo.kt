package android.system.myapplication.models

import android.annotation.SuppressLint
import io.objectbox.annotation.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

@Entity
class Todo(
    @Id(assignable = true)
    var todoId: Long,
    var name: String,
    var description: String,
    var done: Int = 1,
    var priority: Int? = null,
    var date_add: Date,
    var date_done: Date?
) {
    companion object {
        @SuppressLint("SimpleDateFormat")
        private val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")

        fun fromJson(obj: JSONObject): Todo {
            return Todo(
                obj.getLong("todoId"),
                obj.getString("name"),
                obj.getString("description"),
                obj.getInt("done"),
                obj.getInt("priority"),
                dateParser.parse(obj.getString("date_add"))!!,
                runCatching { dateParser.parse(obj.getString("date_done")) }.getOrNull()
            )
        }
    }

    fun toJson(): JSONObject {
        val bodyElement = JSONObject()
        bodyElement.put("todoId", todoId)
        bodyElement.put("name", name)
        bodyElement.put("description", description)
        bodyElement.put("done", done)
        bodyElement.put("priority", priority)
        bodyElement.put("date_add", date_add)
        bodyElement.put("date_done", date_done)
        return bodyElement
    }

    override fun toString(): String {
        return "Todo(todoId=$todoId, name='$name', description='$description', priority=$priority)"
    }
}
