package ru.fefu.helloworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.R
import ru.fefu.helloworld.model.ActivityDataModel
import ru.fefu.helloworld.model.ActivityItem
import ru.fefu.helloworld.model.DateHeaderItem
import ru.fefu.helloworld.util.ActivityIcons

class ActivityAdapter(
    private var activities: List<ActivityDataModel>,
    private val onItemClick: (ActivityDataModel) -> Unit
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    fun updateData(newActivities: List<ActivityDataModel>) {
        this.activities = newActivities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.bind(activity)
    }

    override fun getItemCount() = activities.size

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvActivityType: TextView = itemView.findViewById(R.id.tvActivityType)
        private val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        private val tvTimeAgo: TextView = itemView.findViewById(R.id.tvTimeAgo)
        private val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        private val ivActivityIcon: ImageView = itemView.findViewById(R.id.ivActivityIcon)
        private val tvUsername: TextView? = itemView.findViewById(R.id.tvUsername)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(activities[position])
                }
            }
        }

        fun bind(activity: ActivityDataModel) {
            tvActivityType.text = activity.activityType
            tvDistance.text = activity.distance
            tvTimeAgo.text = activity.timeAgo
            tvDuration.text = activity.duration
            
            // Установка иконки в зависимости от типа активности
            val iconResId = getIconForActivityType(activity.activityType)
            ivActivityIcon.setImageResource(iconResId)
            
            // Показываем/скрываем имя пользователя для активностей других пользователей
            if (tvUsername != null) {
                if (!activity.isMyActivity) {
                    tvUsername.visibility = View.VISIBLE
                    // Генерируем фиктивное имя пользователя на основе типа активности
                    val username = "user_${activity.activityType.lowercase().replace(" ", "_")}"
                    tvUsername.text = "@$username"
                } else {
                    tvUsername.visibility = View.GONE
                }
            }
        }
        
        // Получение иконки в зависимости от типа активности
        private fun getIconForActivityType(activityType: String): Int {
            return when (activityType.lowercase()) {
                "бег" -> R.drawable.ic_run
                "ходьба" -> R.drawable.ic_walk
                "велосипед" -> R.drawable.ic_bicycle
                "плавание" -> R.drawable.ic_swim
                "тренировка" -> R.drawable.ic_workout
                else -> R.drawable.ic_other_activity
            }
        }
    }
} 