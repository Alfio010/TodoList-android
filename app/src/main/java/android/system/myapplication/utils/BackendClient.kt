package android.system.myapplication.utils

import android.system.myapplication.Application
import android.system.myapplication.models.Todo
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject

object BackendClient {
    private const val endPointPrefix = "http://${Application.backendHostname}/todo"
    private const val getTodosEndpoint = "${endPointPrefix}/all"
    private const val updateTodoEndpoint = "${endPointPrefix}/update"
    private const val deleteTodoEndpoint = "${endPointPrefix}/delete"
    private const val sendTodoEndpoint = "${endPointPrefix}/create"
    private const val pingEndpoint = "http://${Application.backendHostname}/ping"

    fun getTodos(onSuccess: (List<Todo>) -> Unit, onError: () -> Unit) {
        val request = StringRequest(
            getTodosEndpoint,
            {
                val todos = JSONArray(it)
                val result = mutableListOf<Todo>()

                for (i in 0 until todos.length()) {
                    val todo = todos.getJSONObject(i)
                    result.add(Todo.fromJson(todo))
                }

                Log.d("aaa-todos", result.toTypedArray().contentDeepToString())

                onSuccess.invoke(result)
            },
            {
                onError.invoke()
                Log.d("aaa-backend-error", it.stackTraceToString())
            }
        )

        Application.requestQueue.add(request)
    }

    fun updateTodo(objTodo: JSONObject) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, updateTodoEndpoint, objTodo,
            { response ->
            },
            { error ->
            }
        )

        Application.requestQueue.add(jsonObjectRequest)
    }

    fun deleteTodo(objTodo: JSONObject) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, deleteTodoEndpoint, objTodo,
            { response ->
            },
            { error ->
            }
        )

        Application.requestQueue.add(jsonObjectRequest)
    }

    fun sendTodo(objTodo: JSONObject) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, sendTodoEndpoint, objTodo,
            { response ->
            },
            { error ->
            }
        )

        Application.requestQueue.add(jsonObjectRequest)
    }

    fun ping() {
        val request = StringRequest(
            Request.Method.GET, pingEndpoint,
            {
            },
            {
                Log.d("aaa-xxx", "error: ${it.stackTraceToString()}")
            }
        )

        Application.requestQueue.add(request)
    }


}
