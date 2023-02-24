package android.system.myapplication

import android.system.myapplication.models.Todo
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import io.objectbox.Box
import io.objectbox.BoxStore

class Application : android.app.Application() {
    companion object {
        const val backendHostname = "192.168.1.59:8080"

        lateinit var database: BoxStore
        lateinit var todos: Box<Todo>
        lateinit var requestQueue: RequestQueue
    }

    override fun onCreate() {
        super.onCreate()
        database = Database.getDatabase(this)
        todos = database.boxFor(Todo::class.java)
        requestQueue = Volley.newRequestQueue(this)
    }
}
