package android.system.myapplication.utils

import android.system.myapplication.Application
import android.system.myapplication.models.Todo
import android.system.myapplication.models.Todo_
import io.objectbox.kotlin.equal
import org.json.JSONArray
import java.util.Date
import java.text.SimpleDateFormat
import java.util.*

fun dateFormatter(date: Date): String {
    val formatter = SimpleDateFormat("dd/M/yyyy HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("GMT+2")
    val formattedDate = formatter.format(date)
    return formattedDate
}

fun findByID(id: Long): MutableList<Todo> {
    val query = Application.todos
        .query(
            Todo_.todoId equal id
        )
        .build()
        .find()
    return query
}

fun sortedList(): MutableList<Todo> {
    val todo = Application.todos
        .query()
        .order(Todo_.done)
        .orderDesc(Todo_.date_add)
        .build()
        .find()
    return todo
}

fun todosArray(todos: List<Todo>): JSONArray {
    val jsonTodos = JSONArray()
    todos.map { it.toJson() }.forEachIndexed(jsonTodos::put)

    return jsonTodos
}

fun lastTodo(): Todo? {
    val todo = Application.todos
        .query()
        .orderDesc(Todo_.todoId)
        .build()
        .findFirst()
    return todo
}

