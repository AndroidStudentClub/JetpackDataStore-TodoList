package ru.androidschool.jetpackdatastoretodolist.data

import java.util.*

enum class TaskPriority {
    HIGH, NORMAL, LOW
}
data class Task(
    val name: String,
    val deadline: Date,
    val priority: TaskPriority,
    val completed: Boolean = false
)
