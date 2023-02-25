package android.system.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.system.myapplication.models.Todo
import android.system.myapplication.utils.BackendClient.deleteTodo
import android.system.myapplication.utils.BackendClient.updateTodo
import android.system.myapplication.utils.dateFormatter
import android.system.myapplication.utils.findByID
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import io.objectbox.Box
import org.json.JSONObject
import java.util.*


class CustomAdapter
    (context: Context, idRowCustom: Int, list: List<Todo?>, refreshCallback: () -> Unit) :
    ArrayAdapter<Todo?>(context, idRowCustom, list) {
    var g: Todo? = null
    var todos: Box<Todo> = Database.getDatabase(context).boxFor(Todo::class.java)

    var contextt: Context = context
    var refreshCallback: () -> Unit = refreshCallback

    @SuppressLint("SetTextI18n", "ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView: View?
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        convertView = inflater.inflate(R.layout.rowcustom, null)

        val tvNome = convertView!!.findViewById<View>(R.id.tvNome) as TextView
        val tvDescrizione = convertView.findViewById<View>(R.id.tvDescrizione) as TextView
        val tvDate = convertView.findViewById<View>(R.id.tvDate) as TextView
        val swTodo = convertView.findViewById<View>(R.id.swToDo) as Switch

        g = getItem(position)
        val name = g?.name
        var description = g?.description

        tvNome.text = name

        when (g?.priority) {
            1 -> tvNome.setTextColor(Color.parseColor("#FFD700"))
            2 -> tvNome.setTextColor(Color.parseColor("#5E5B63"))
            3 -> tvNome.setTextColor(Color.parseColor("#FF0000"))
        }

        if (g?.done == 1) {
            swTodo.isChecked = true
        }

        if (description != null) {
            if (description.length > 27) {
                description = description.subSequence(0, 27).toString() + "..."
            }
        }
        tvDescrizione.text = description

        if (g?.date_add != null) {

            if (g?.date_done != null) {
                tvDate.text = dateFormatter(g?.date_add!!) + " - " + dateFormatter(g?.date_done!!)
            } else {
                tvDate.text = dateFormatter(g?.date_add!!)
            }

        } else {
            tvDate.visibility = View.GONE
        }

        swTodo.setOnClickListener {
            g = getItem(position)
            val id = g?.todoId!!

            val todo: Todo = todos.get(id)
            val nowIsDone = swTodo.isChecked
            val oldIsDone = todo.done == 1

            if (oldIsDone && !nowIsDone) {
                todo.date_done = null
            }

            if (!oldIsDone && nowIsDone) {
                todo.date_done = Date()
            }

            todo.done = if (nowIsDone) {
                1
            } else {
                0
            }

            todos.put(todo)
            refreshCallback.invoke()

            val query = findByID(id)

            val bodyElement = JSONObject()
            bodyElement.put("todoId", query[0].todoId)
            bodyElement.put("done", query[0].done)
            bodyElement.put("date_done", query[0].date_done)

            updateTodo(bodyElement)

        }

        tvNome.setOnClickListener {
            g = getItem(position)

            val builder = AlertDialog.Builder(contextt)
            builder.setTitle(g?.name)
            builder.setMessage(g?.description)
                .setPositiveButton(
                    "Elimina"
                ) { _, _ ->
                    val gID = g?.todoId
                    val todo: Todo = todos.get(gID!!)

                    val query = findByID(gID)

                    val bodyElement = JSONObject()
                    bodyElement.put("todoId", query[0].todoId)

                    deleteTodo(bodyElement)

                    todos.remove(todo)
                    refreshCallback.invoke()

                }
                .setNeutralButton(
                    "Indietro"
                ) { _, _ ->
                }

            builder.create()
            builder.show()
        }

        tvDescrizione.setOnClickListener {
            g = getItem(position)

            val builder = AlertDialog.Builder(contextt)
            builder.setTitle(g?.name)
            builder.setMessage(g?.description)
                .setPositiveButton(
                    "Elimina"
                ) { _, _ ->
                    val gID = g?.todoId
                    val todo: Todo = todos.get(gID!!)

                    val query = findByID(gID)

                    val bodyElement = JSONObject()
                    bodyElement.put("todoId", query[0].todoId)

                    deleteTodo(bodyElement)

                    todos.remove(todo)
                    refreshCallback.invoke()

                }
                .setNeutralButton(
                    "Indietro"
                ) { _, _ ->
                }

            builder.create()
            builder.show()
        }

        return convertView
    }
}
