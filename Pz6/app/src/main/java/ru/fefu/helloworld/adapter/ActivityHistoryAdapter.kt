package ru.fefu.helloworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.R
import ru.fefu.helloworld.model.ActivityEntity
import ru.fefu.helloworld.model.ActivityTypeEnum
import ru.fefu.helloworld.model.Coordinate
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class ActivityHistoryAdapter(
    private val activityId: Long,
    private val onItemClick: (ActivityEntity) -> Unit
) : RecyclerView.Adapter<ActivityHistoryAdapter.ActivityViewHolder>() {

    private var activities: List<ActivityEntity> = emptyList()

    fun setActivities(activities: List<ActivityEntity>) {
        this.activities = activities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_history, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.bind(activity)
    }

    override fun getItemCount(): Int = activities.size

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageActivityType: ImageView = itemView.findViewById(R.id.imageActivityType)
        private val textActivityType: TextView = itemView.findViewById(R.id.textActivityType)
        private val textActivityDate: TextView = itemView.findViewById(R.id.textActivityDate)
        private val textActivityDuration: TextView = itemView.findViewById(R.id.textActivityDuration)
        private val textActivityDistance: TextView = itemView.findViewById(R.id.textActivityDistance)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(activities[position])
                }
            }
        }

        fun bind(activity: ActivityEntity) {
            // Установка иконки типа активности
            imageActivityType.setImageResource(activity.activityType.iconResId)
            
            // Установка названия типа активности
            textActivityType.text = activity.activityType.stringId
            
            // Форматирование и установка даты
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            textActivityDate.text = dateFormat.format(activity.startTime)
            
            // Расчет и установка длительности
            val durationInMillis = activity.endTime.time - activity.startTime.time
            val hours = TimeUnit.MILLISECONDS.toHours(durationInMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) % 60
            
            val durationText = when {
                hours > 0 -> "$hours ч $minutes мин"
                else -> "$minutes мин"
            }
            textActivityDuration.text = durationText
            
            // Расчет и установка расстояния (на основе координат)
            val distanceInMeters = calculateDistance(activity.coordinates)
            val distanceText = when {
                distanceInMeters >= 1000 -> String.format("%.1f км", distanceInMeters / 1000)
                else -> "${distanceInMeters.toInt()} м"
            }
            textActivityDistance.text = distanceText
        }
        
        // Примерный расчет расстояния на основе координат
        private fun calculateDistance(coordinates: List<Coordinate>): Double {
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
} 