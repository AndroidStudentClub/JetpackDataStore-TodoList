package ru.androidschool.jetpackdatastoretodolist.data

import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.*

object MockTasksRepository {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    val tasks = flowOf(
        listOf(
            Task(
                name = "Пройти туториал по DataStore",
                deadline = simpleDateFormat.parse("2020-07-03")!!,
                priority = TaskPriority.LOW,
                completed = true
            ),
            Task(
                name = "Записаться на Android-интенсив",
                deadline = simpleDateFormat.parse("2020-04-03")!!,
                priority = TaskPriority.NORMAL,
                completed = true
            ),
            Task(
                name = "Скачать код проекта", deadline = simpleDateFormat.parse("2020-05-03")!!,
                priority = TaskPriority.LOW
            ),
            Task(
                name = "Реализовать каждый из шагов",
                deadline = simpleDateFormat.parse("2020-06-03")!!,
                priority = TaskPriority.HIGH
            ),
            Task(
                name = "Создать резюме",
                deadline = Date(),
                priority = TaskPriority.NORMAL
            ),
            Task(
                name = "Почиать про Kotlin",
                deadline = simpleDateFormat.parse("2020-04-03")!!,
                priority = TaskPriority.HIGH
            ),
            Task(
                name = "Пройти урок по переходу от SharedPreferences",
                deadline = Date(),
                priority = TaskPriority.HIGH
            )
        )
    )
}
