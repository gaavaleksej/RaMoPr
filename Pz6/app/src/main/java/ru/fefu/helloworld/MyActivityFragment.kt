package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.fefu.helloworld.adapter.ActivityHistoryAdapter
import ru.fefu.helloworld.model.ActivityData
import ru.fefu.helloworld.viewmodel.ActivityViewModel
import java.util.*
import java.text.SimpleDateFormat

class MyActivityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivityHistoryAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_activity, container, false)
        // Инициализация ViewModel
        viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewMyActivities)
        recyclerView.layoutManager = LinearLayoutManager(context)
        // Создание и установка адаптера с обработчиком нажатия
        adapter = ActivityHistoryAdapter(0) { activity ->
            try {
                // Преобразуем ActivityEntity в ActivityData
                val activityData = ActivityData(
                    activityType = activity.activityType.stringId,
                    distance = formatDistance(calculateDistance(activity.coordinates)),
                    timeAgo = getTimeAgo(activity.startTime.time),
                    duration = formatDuration(activity.endTime.time - activity.startTime.time),
                    startTime = formatTime(activity.startTime.time),
                    finishTime = formatTime(activity.endTime.time),
                    date = formatDate(activity.startTime)
                )
                
                // Запуск активности с деталями
                val intent = ActivityDetailActivity.newIntent(requireContext(), activityData)
                startActivity(intent)
            } catch (e: Exception) {
                // Логирование ошибки
                Toast.makeText(
                    requireContext(),
                    "Ошибка при открытии деталей: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        recyclerView.adapter = adapter
        
        // Наблюдение за изменениями в списке активностей
        viewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            adapter.setActivities(activities)
        }
        // Настройка FAB
        fab = view.findViewById(R.id.fabAddActivity)
        fab.setOnClickListener {
            val intent = Intent(context, NewActivityActivity::class.java)
            startActivity(intent)
        }
        
        return view
    }
    
    // Вспомогательные методы для форматирования данных
    private fun formatDistance(distanceInMeters: Double): String {
        return when {
            distanceInMeters >= 1000 -> String.format("%.1f км", distanceInMeters / 1000)
            else -> "${distanceInMeters.toInt()} м"
        }
    }
    
    private fun formatDuration(durationInMillis: Long): String {
        val hours = durationInMillis / (1000 * 60 * 60)
        val minutes = (durationInMillis / (1000 * 60)) % 60
        
        return when {
            hours > 0 -> "$hours ч $minutes мин"
            else -> "$minutes мин"
        }
    }
    
    private fun formatTime(timeInMillis: Long): String {
        val dateFormat = android.text.format.DateFormat.getTimeFormat(requireContext())
        return dateFormat.format(timeInMillis)
    }
    
    private fun formatDate(date: java.util.Date): String {
        val now = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }
        val dateInCalendar = Calendar.getInstance().apply {
            time = date
        }

        return when {
            isSameDay(dateInCalendar, now) -> "Сегодня"
            isSameDay(dateInCalendar, yesterday) -> "Вчера"
            else -> {
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                dateFormat.format(date)
            }
        }
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
    
    private fun getTimeAgo(timeInMillis: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timeInMillis
        
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> "$days ${pluralize(days.toInt(), "день", "дня", "дней")} назад"
            hours > 0 -> "$hours ${pluralize(hours.toInt(), "час", "часа", "часов")} назад"
            minutes > 0 -> "$minutes ${pluralize(minutes.toInt(), "минута", "минуты", "минут")} назад"
            else -> "только что"
        }
    }
    
    private fun pluralize(count: Int, one: String, few: String, many: String): String {
        val mod10 = count % 10
        val mod100 = count % 100
        
        return when {
            mod100 in 11..19 -> many
            mod10 == 1 -> one
            mod10 in 2..4 -> few
            else -> many
        }
    }
    
    // Метод для расчета расстояния на основе координат
    private fun calculateDistance(coordinates: List<ru.fefu.helloworld.model.Coordinate>): Double {
        if (coordinates.size < 2) return 0.0
        var totalDistance = 0.0
        for (i in 0 until coordinates.size - 1) {
            val start = coordinates[i]
            val end = coordinates[i + 1]
            totalDistance += calculateHaversineDistance(
                start.latitude, start.longitude,
                end.latitude, end.longitude
            )
        }
        
        return totalDistance
    }
    
    // Расчет расстояния между двумя точками по формуле гаверсинуса
    private fun calculateHaversineDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val R = 6371e3 // радиус Земли в метрах
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaPhi = Math.toRadians(lat2 - lat1)
        val deltaLambda = Math.toRadians(lon2 - lon1)
        
        val a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        
        return R * c // расстояние в метрах
    }
}