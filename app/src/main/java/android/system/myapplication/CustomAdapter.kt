package android.system.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.system.myapplication.models.Todo
import android.system.myapplication.utils.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import io.objectbox.Box
import java.util.*

class CustomAdapter
    (context: Context, idRowCustom: Int, list: List<Todo?>, var refreshCallback: () -> Unit) :
    ArrayAdapter<Todo?>(context, idRowCustom, list) {
    var todoItem: Todo? = null
    var todos: Box<Todo> = Database.getDatabase(context).boxFor(Todo::class.java)

    var contextt: Context = context

    @SuppressLint("ViewHolder", "SetTextI18n", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView2: View?
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        convertView2 = inflater.inflate(R.layout.rowcustom, null)

        val tvNome = convertView2.findViewById<View>(R.id.tvNome) as MaterialTextView
        val tvDescription = convertView2.findViewById<View>(R.id.tvDescrizione) as MaterialTextView
        val tvDate = convertView2.findViewById<View>(R.id.tvDate) as MaterialTextView
        val swTodo = convertView2.findViewById<View>(R.id.swToDo) as SwitchMaterial

        todoItem = getItem(position)
        val name = todoItem?.name
        var description = todoItem?.description

        tvNome.text = name

        when (todoItem?.priority) {
            1 -> tvNome.setTextColor(ContextCompat.getColor(context, R.color.medium))
            2 -> tvNome.setTextColor(ContextCompat.getColor(context, R.color.standard))
            3 -> tvNome.setTextColor(ContextCompat.getColor(context, R.color.high))
        }

        if (todoItem?.done == 1) {
            swTodo.isChecked = true
        }

        if (description != null) {
            if (description.length > 27) {
                description = description.subSequence(0, 27).toString() + "..."
            }
        }
        tvDescription.text = description

        if (todoItem?.date_add != null) {

            val formattedDateAdd = todoItem?.date_add?.let { DateUtils.dateFormatter(it) }

            if (todoItem?.date_done != null) {
                val formattedDateDone = todoItem?.date_done?.let { DateUtils.dateFormatter(it) }
                tvDate.text = "$formattedDateAdd - $formattedDateDone"
            } else {
                tvDate.text = formattedDateAdd
            }

        } else {
            tvDate.visibility = View.GONE
        }

        swTodo.setOnClickListener {
            todoItem = getItem(position)

            val todo: Todo = todos.get(todoItem?.todoId!!)
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
        }

        fun tvOnListener(textView: TextView) {
            textView.setOnClickListener {
                todoItem = getItem(position)

                val builder = MaterialAlertDialogBuilder(contextt)
                builder.setTitle(todoItem?.name)
                builder.setMessage(todoItem?.description)
                    .setPositiveButton(context.getString(R.string.delete)) { _, _ ->
                        val gID = todoItem?.todoId
                        val todo: Todo = todos.get(gID!!)
                        todos.remove(todo)
                        refreshCallback.invoke()
                    }
                    .setNeutralButton(context.getString(R.string.back)) { _, _ -> }

                builder.create()
                builder.show()
            }
        }

        tvOnListener(tvNome)
        tvOnListener(tvDescription)

        return convertView2
    }
}
