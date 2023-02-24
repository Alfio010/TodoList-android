package android.system.myapplication

import android.content.Intent
import android.os.Bundle
import android.system.myapplication.utils.sortedList
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var adapter: CustomAdapter
    lateinit var list: ListView


    private fun refreshList() {

       val todo = sortedList()

        adapter = CustomAdapter(this@MainActivity, R.layout.rowcustom, todo, ::refreshList)
        list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        list = findViewById<ListView>(R.id.list1)

        val bAddTodo = findViewById<Button>(R.id.bAddTodo)
        val bSettings = findViewById<Button>(R.id.bSettings)

        refreshList()

        list.setOnItemClickListener { parent, view, position, id ->
            val g = parent.getItemAtPosition(position)
            Toast.makeText(this, g.toString(), Toast.LENGTH_SHORT).show()
        }



        bAddTodo.setOnClickListener {

            val intent = Intent(this, addTodo::class.java)
            startActivity(intent)

        }

        bSettings.setOnClickListener {

            val intent = Intent(this, Settings::class.java)
            startActivity(intent)

        }


    }
}
