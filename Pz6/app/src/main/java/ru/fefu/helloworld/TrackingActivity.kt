package ru.fefu.helloworld

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.fefu.helloworld.model.ActivityTypeEnum
import ru.fefu.helloworld.model.Coordinate
import ru.fefu.helloworld.viewmodel.ActivityViewModel
import java.util.Date
import kotlin.random.Random

class TrackingActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ACTIVITY_TYPE_NAME = "extra_activity_type_name"
    }

    private lateinit var tvActivityName: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvTimer: TextView
    private lateinit var fabPause: FloatingActionButton
    private lateinit var fabStop: FloatingActionButton
    private lateinit var viewModel: ActivityViewModel

    private var activityTypeName: String = ""
    private var distanceInMeters: Int = 0
    private var isTracking: Boolean = true
    private var seconds: Int = 0
    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            seconds++
            updateTimerDisplay()
            handler.postDelayed(this, 1000)
        }
    }
    
    // Время начала активности
    private val startTime = Date()
    // Координаты для имитации пути активности
    private val coordinates = mutableListOf<Coordinate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]

        // Получение типа активности из Intent
        activityTypeName = intent.getStringExtra(EXTRA_ACTIVITY_TYPE_NAME) ?: "Активность"

        // Инициализация UI элементов
        tvActivityName = findViewById(R.id.tvActivityName)
        tvDistance = findViewById(R.id.tvDistance)
        tvTimer = findViewById(R.id.tvTimer)
        fabPause = findViewById(R.id.fabPause)
        fabStop = findViewById(R.id.fabStop)

        // Установка названия активности
        tvActivityName.text = activityTypeName

        // Настройка обработчиков для кнопок
        fabPause.setOnClickListener {
            toggleTracking()
        }

        fabStop.setOnClickListener {
            stopTracking()
        }

        // Запуск таймера и отслеживания активности
        startTracking()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
    }

    // Запуск отслеживания активности
    private fun startTracking() {
        isTracking = true
        handler.post(timerRunnable)
        updateDistance() // Начинаем имитировать изменение расстояния
        // Добавляем начальную координату (используем Владивосток как исходную точку)
        val vladivostokLat = 43.115536
        val vladivostokLng = 131.885485
        coordinates.add(Coordinate(vladivostokLat, vladivostokLng))
    }

    // Переключение состояния отслеживания (пауза/возобновление)
    private fun toggleTracking() {
        if (isTracking) {
            // Поставить на паузу
            handler.removeCallbacks(timerRunnable)
            fabPause.setImageResource(android.R.drawable.ic_media_play)
        } else {
            // Возобновить
            handler.post(timerRunnable)
            fabPause.setImageResource(android.R.drawable.ic_media_pause)
        }
        isTracking = !isTracking
    }

    // Остановка отслеживания и завершение активности
    private fun stopTracking() {
        handler.removeCallbacks(timerRunnable)
        
        // Сохраняем активность в базу данных
        saveActivity()
        
        finish()
    }
    
    // Сохранение активности в базу данных
    private fun saveActivity() {
        // Преобразуем название активности в ActivityTypeEnum
        val activityType = when (activityTypeName.lowercase()) {
            "велосипед" -> ActivityTypeEnum.CYCLING
            "бег" -> ActivityTypeEnum.RUNNING
            "шаг" -> ActivityTypeEnum.WALKING
            else -> ActivityTypeEnum.WALKING // По умолчанию используем ходьбу
        }
        // Время завершения - текущее время
        val endTime = Date()
        // Создаем и сохраняем активность
        viewModel.createActivity(
            activityType = activityType,
            startTime = startTime,
            endTime = endTime,
            coordinates = coordinates
        )
        // Уведомляем пользователя
        Toast.makeText(this, "Активность успешно сохранена", Toast.LENGTH_SHORT).show()
    }

    // Обновление отображения таймера
    private fun updateTimerDisplay() {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        
        tvTimer.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    // Имитация увеличения расстояния и добавления координат
    private fun updateDistance() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isTracking) {
                // Увеличиваем расстояние
                distanceInMeters += 10 // Увеличиваем на 10 метров каждую секунду
                // Добавляем новую координату (немного отклоняемся от предыдущей)
                if (coordinates.isNotEmpty()) {
                    val lastCoord = coordinates.last()
                    val newLat = lastCoord.latitude + Random.nextDouble(-0.0001, 0.0001)
                    val newLng = lastCoord.longitude + Random.nextDouble(-0.0001, 0.0001)
                    coordinates.add(Coordinate(newLat, newLng))
                }
                // Обновляем UI
                val distanceText = if (distanceInMeters >= 1000) {
                    val km = distanceInMeters / 1000f
                    String.format("%.2f км", km)
                } else {
                    "$distanceInMeters м"
                }
                tvDistance.text = distanceText
                updateDistance() // Рекурсивный вызов для имитации постоянного обновления
            }
        }, 1000)
    }
} 