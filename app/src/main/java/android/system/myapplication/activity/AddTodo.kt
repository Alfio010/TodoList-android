package android.system.myapplication.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.system.myapplication.Database
import android.system.myapplication.R
import android.system.myapplication.models.Todo
import android.system.myapplication.utils.DateUtils.formatCalendar
import android.system.myapplication.utils.ScheduleNotification
import android.system.myapplication.utils.SomeUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.objectbox.Box
import java.text.SimpleDateFormat
import java.util.*

class AddTodo : AppCompatActivity() {
    private lateinit var todos: Box<Todo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        supportActionBar?.hide()

        val myCalendar = Calendar.getInstance()

        val bAdd1 = findViewById<MaterialButton>(R.id.bAddd)
        val etName = findViewById<EditText>(R.id.etName)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etTime = findViewById<EditText>(R.id.etTime)
        val bResetEt = findViewById<FloatingActionButton>(R.id.bResetEt)

        bResetEt.setOnClickListener {
            etTime.text.clear()
            etDate.text.clear()
        }

        val date =
            OnDateSetListener { _, year, month, day ->
                etDate.setText(formatCalendar(myCalendar, year, month, day))
            }

        etDate.setOnClickListener {
            DatePickerDialog(
                this@AddTodo,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val time =
            OnTimeSetListener { _, hour, minute ->
                myCalendar.set(Calendar.HOUR, hour)
                myCalendar.set(Calendar.MINUTE, minute)
                val myFormat = "HH:mm"
                val dateFormat = SimpleDateFormat(myFormat, Locale.getDefault())
                etTime.setText(dateFormat.format(myCalendar.time))
            }

        etTime.setOnClickListener {
            TimePickerDialog(
                this@AddTodo,
                time,
                myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        todos = Database.getDatabase(baseContext).boxFor(Todo::class.java)

        val spinner = findViewById<Spinner>(R.id.spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
            spinner.adapter = adapter
        }

        bAdd1.setOnClickListener {

            val alertTime = etTime.text.toString()
            val alertDate = etDate.text.toString()

            if (
                (alertDate.isBlank() && alertTime.isNotBlank()) ||
                (alertDate.isNotBlank() && alertTime.isBlank())
            ) {
                SomeUtils.showToast(getString(R.string.fill_date_time), this)
                return@setOnClickListener
            }

            val input = etName.text.toString().trim()

            if (input.isBlank()) {
                SomeUtils.showToast(getString(R.string.enter_name), this)
            } else {

                val priority = when (spinner.selectedItem.toString()) {
                    getString(R.string.low) -> 1
                    getString(R.string.normal) -> 2
                    getString(R.string.high) -> 3
                    else -> {
                        0
                    }
                }

                val currentDate = Date()

                todos.put(
                    Todo(
                        0,
                        etName.text.toString(),
                        etDescription.text.toString(),
                        0,
                        priority,
                        currentDate,
                        null
                    )
                )

                val calendarAlarm = Calendar.getInstance()
                val simpleDateFormat = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
                )

                // TODO: add request perm notification | work in progress (scheduled notification not working)

                ScheduleNotification.createNotificationChannel(this)

                calendarAlarm.time = simpleDateFormat.parse("$alertDate $alertTime")!!
                ScheduleNotification.scheduleNotification(calendarAlarm, this)

                Log.d("aaa-time", "${calendarAlarm.time}")

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("on", true)
                finish()
                startActivity(intent)
            }
        }
    }
}
