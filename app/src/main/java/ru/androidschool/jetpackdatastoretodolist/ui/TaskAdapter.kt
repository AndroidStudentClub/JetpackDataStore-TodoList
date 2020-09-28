package ru.androidschool.jetpackdatastoretodolist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.view.*
import ru.androidschool.jetpackdatastoretodolist.R
import ru.androidschool.jetpackdatastoretodolist.data.Task
import ru.androidschool.jetpackdatastoretodolist.data.TaskPriority
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val tasks: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    class TaskViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)

        fun bind(task: Task) {
            containerView.task.text = task.name
            containerView.deadline.text = dateFormat.format(task.deadline)

            // set the priority color based on the task priority
            val textParams = when (task.priority) {
                TaskPriority.HIGH -> R.color.red to R.string.high
                TaskPriority.NORMAL -> R.color.yellow to R.string.normal
                TaskPriority.LOW -> R.color.green to R.string.low
            }
            containerView.category_marker.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    textParams.first
                )
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(task = tasks[position])
    }

    fun refresh(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}