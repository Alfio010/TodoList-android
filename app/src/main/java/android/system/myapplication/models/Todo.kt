package android.system.myapplication.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
class Todo(
    @Id
    var todoId: Long,
    var name: String,
    var description: String,
    var done: Int = 1,
    var priority: Int? = null,
    var date_add: Date,
    var date_done: Date?

) {
    override fun toString(): String {
        return "Todo(todoId=$todoId, name='$name', description='$description', done=$done," +
                " priority=$priority, date_add=$date_add, date_done=$date_done)"
    }
}



