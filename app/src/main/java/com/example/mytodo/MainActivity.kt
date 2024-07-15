package com.example.mytodo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.todolist.Task
import com.example.todolist.TaskAdapter

class MainActivity : ComponentActivity() {
    private lateinit var spinnerPriority: Spinner
    private lateinit var edTaskTitle: EditText
    private lateinit var edTaskDescription: EditText
    private lateinit var btAddTask: Button
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()
    private var filteredTaskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerPriority = findViewById(R.id.spinnerPriority)
        edTaskTitle = findViewById(R.id.edTaskTitle)
        edTaskDescription = findViewById(R.id.edTaskDescription)
        btAddTask = findViewById(R.id.btAddTask)
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)

        // Setup spinner
        val priorities = resources.getStringArray(R.array.priority_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        spinnerPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedPriority = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity, "Selected: $selectedPriority", Toast.LENGTH_SHORT).show()
                filterTasks(selectedPriority)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Setup RecyclerView
        taskAdapter = TaskAdapter(filteredTaskList) { task -> deleteTask(task) }
        recyclerViewTasks.adapter = taskAdapter
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        btAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val taskTitle = edTaskTitle.text.toString()
        val taskDescription = edTaskDescription.text.toString()
        val taskPriority = spinnerPriority.selectedItem.toString()

        if (taskTitle.isEmpty() || taskDescription.isEmpty()) {
            Toast.makeText(this, "Please enter both task title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val task = Task(taskTitle, taskDescription, taskPriority)
        taskList.add(task)
        filterTasks(spinnerPriority.selectedItem.toString())
        edTaskTitle.text.clear()
        edTaskDescription.text.clear()
    }

    private fun deleteTask(task: Task) {
        taskList.remove(task)
        filterTasks(spinnerPriority.selectedItem.toString())
    }

    private fun filterTasks(priority: String) {
        filteredTaskList.clear()
        if (priority == "All") {
            filteredTaskList.addAll(taskList)
        } else {
            filteredTaskList.addAll(taskList.filter { it.priority == priority })
        }
        taskAdapter.notifyDataSetChanged()
    }
}
