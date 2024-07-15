package com.example.todolist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.R

class TaskAdapter(private val tasks: MutableList<Task>, private val deleteTask: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskPriority: TextView = itemView.findViewById(R.id.taskPriority)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.taskTitle.text = currentTask.title
        holder.taskDescription.text = currentTask.description
        holder.taskPriority.text = currentTask.priority

        // Set color based on priority
        when (currentTask.priority) {
            "High" -> holder.taskPriority.setTextColor(Color.RED)
            "Medium" -> holder.taskPriority.setTextColor(Color.BLUE)
            "Low" -> holder.taskPriority.setTextColor(Color.parseColor("#1b4332"))
        }

        holder.deleteButton.setOnClickListener {
            deleteTask(currentTask)
        }
    }

    override fun getItemCount() = tasks.size
}
