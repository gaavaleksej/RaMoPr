package ru.fefu.helloworld

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TrackingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ACTIVITY_TYPE_NAME = "extra_activity_type_name"
    }
    private lateinit var tvActivityName: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvTimer: TextView
    private lateinit var fabPause: FloatingActionButton
    private lateinit var fabStop: FloatingActionButton
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
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
        // В реальном приложении здесь был бы код для сохранения результатов активности
        finish()
    }
    // Обновление отображения таймера
    private fun updateTimerDisplay() {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        
        tvTimer.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
    // Имитация увеличения расстояния
    private fun updateDistance() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isTracking) {
                distanceInMeters += 10 //Увеличиваем на 10 метров каждую секунду
                
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