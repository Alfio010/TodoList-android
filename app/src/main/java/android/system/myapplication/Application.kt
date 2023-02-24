package android.system.myapplication

import android.system.myapplication.models.Todo
import io.objectbox.Box
import io.objectbox.BoxStore

class Application : android.app.Application() {
    companion object {
        lateinit var database: BoxStore
        lateinit var todos: Box<Todo>
    }

    override fun onCreate() {
        super.onCreate()
        database = Database.getDatabase(this)
        todos = database.boxFor(Todo::class.java)
    }
}
