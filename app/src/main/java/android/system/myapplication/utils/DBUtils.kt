package android.system.myapplication.utils

import android.system.myapplication.Application
import android.system.myapplication.models.Todo
import android.system.myapplication.models.Todo_
import io.objectbox.kotlin.equal
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

fun dateFormatter(date: Date): String {
    val formatter = SimpleDateFormat("dd/M/yyyy HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("GMT+2")
    return formatter.format(date)
}

fun findByID(id: Long): MutableList<Todo> {
    return Application.todos
        .query(
            Todo_.todoId equal id
        )
        .build()
        .find()
}

fun sortedList(): MutableList<Todo> {
    return Application.todos
        .query()
        .order(Todo_.done)
        .orderDesc(Todo_.date_add)
        .build()
        .find()
}

fun todosArray(todos: List<Todo>): JSONArray {
    val jsonTodos = JSONArray()
    todos.map { it.toJson() }.forEachIndexed(jsonTodos::put)

    return jsonTodos
}

fun lastTodo(): Todo? {
    return Application.todos
        .query()
        .orderDesc(Todo_.todoId)
        .build()
        .findFirst()
}

