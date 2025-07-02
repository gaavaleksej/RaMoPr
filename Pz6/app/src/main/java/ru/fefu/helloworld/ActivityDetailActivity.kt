package ru.fefu.helloworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.model.ActivityData
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ACTIVITY_DATA = "extra_activity_data"

        // Метод для создания Intent для запуска этой активности
        fun newIntent(context: Context, activityData: ActivityData): Intent {
            return Intent(context, ActivityDetailActivity::class.java).apply {
                putExtra(EXTRA_ACTIVITY_DATA, activityData)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // Получение данных об активности из Intent
        val activityData = intent.getParcelableExtra<ActivityData>(EXTRA_ACTIVITY_DATA)
            ?: throw IllegalArgumentException("ActivityData must be provided")
        // Настройка тулбара
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        // Инициализация UI элементов
        val tvActivityType = findViewById<TextView>(R.id.tvActivityType)
        val tvDistance = findViewById<TextView>(R.id.tvDistance)
        val tvTimeAgo = findViewById<TextView>(R.id.tvTimeAgo)
        val tvDuration = findViewById<TextView>(R.id.tvDuration)
        val tvStartTime = findViewById<TextView>(R.id.tvStartTime)
        val tvFinishTime = findViewById<TextView>(R.id.tvFinishTime)
        val etComment = findViewById<EditText>(R.id.etComment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Заполнение данными
        tvActivityType.text = activityData.activityType
        tvDistance.text = activityData.distance
        tvTimeAgo.text = activityData.timeAgo
        tvDuration.text = activityData.duration
        tvStartTime.text = activityData.startTime
        tvFinishTime.text = activityData.finishTime

        // Настройка навигации
        bottomNavigationView.selectedItemId = R.id.nav_activity
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    // Уже на экране активностей
                    true
                }
                R.id.nav_profile -> {
                    // Переход на экран профиля
                    finish() // Закрываем текущий экран
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("openProfile", true)
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // Обработка нажатия на кнопку "Назад" в тулбаре
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 