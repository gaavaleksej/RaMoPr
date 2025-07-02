package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.adapter.ActivityTypeAdapter
import ru.fefu.helloworld.model.ActivityType

class NewActivityActivity : AppCompatActivity() {

    private lateinit var rvActivityTypes: RecyclerView
    private lateinit var btnStart: Button
    private lateinit var mapView: View
    private lateinit var adapter: ActivityTypeAdapter
    private var selectedActivityType: ActivityType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_activity)
        // Инициализация UI элементов
        rvActivityTypes = findViewById(R.id.rvActivityTypes)
        btnStart = findViewById(R.id.btnStart)
        mapView = findViewById(R.id.mapView)
        // Настройка RecyclerView
        rvActivityTypes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // Создание и установка адаптера
        adapter = ActivityTypeAdapter(getActivityTypes()) { activityType ->
            selectedActivityType = activityType
            updateStartButtonState()
        }
        rvActivityTypes.adapter = adapter
        // Настройка кнопки "Начать"
        btnStart.setOnClickListener {
            selectedActivityType?.let { activityType ->
                startTrackingActivity(activityType)
            }
        }
        // Начальное состояние кнопки "Начать"
        updateStartButtonState()
    }

    // Получение списка типов активности
    private fun getActivityTypes(): List<ActivityType> {
        return listOf(
            ActivityType(1, "Велосипед", R.drawable.ic_bike),
            ActivityType(2, "Бег", R.drawable.ic_run),
            ActivityType(3, "Шаг", R.drawable.ic_walk)
        )
    }
    // Обновление состояния кнопки "Начать"
    private fun updateStartButtonState() {
        btnStart.isEnabled = selectedActivityType != null
        btnStart.alpha = if (selectedActivityType != null) 1.0f else 0.5f
    }
    // Запуск экрана отслеживания активности
    private fun startTrackingActivity(activityType: ActivityType) {
        val intent = Intent(this, TrackingActivity::class.java).apply {
            putExtra(TrackingActivity.EXTRA_ACTIVITY_TYPE_NAME, activityType.name)
        }
        startActivity(intent)
    }
} 