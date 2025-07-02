package ru.fefu.helloworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.R
import ru.fefu.helloworld.model.ActivityData
import ru.fefu.helloworld.model.ActivityItem
import ru.fefu.helloworld.model.DateHeaderItem
import ru.fefu.helloworld.util.ActivityIcons

class ActivityAdapter(
    private var items: List<ActivityItem>,
    private val onActivityClicked: (ActivityData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Обновление данных в адаптере
    fun updateData(newItems: List<ActivityItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    // Определение типа представления в зависимости от типа элемента
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DateHeaderItem -> ActivityItem.VIEW_TYPE_DATE_HEADER
            is ActivityData -> ActivityItem.VIEW_TYPE_ACTIVITY
        }
    }

    // Создание ViewHolder в зависимости от типа представления
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ActivityItem.VIEW_TYPE_DATE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date_header, parent, false)
                DateHeaderViewHolder(view)
            }
            ActivityItem.VIEW_TYPE_ACTIVITY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_activity, parent, false)
                ActivityViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is DateHeaderItem -> (holder as DateHeaderViewHolder).bind(item)
            is ActivityData -> (holder as ActivityViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size

    // ViewHolder для заголовка с датой
    inner class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDateHeader: TextView = itemView.findViewById(R.id.tvDateHeader)

        fun bind(item: DateHeaderItem) {
            tvDateHeader.text = item.date
        }
    }

    // ViewHolder для элемента активности
    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        private val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        private val tvActivityType: TextView = itemView.findViewById(R.id.tvActivityType)
        private val tvTimeAgo: TextView = itemView.findViewById(R.id.tvTimeAgo)
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val ivActivityIcon: ImageView = itemView.findViewById(R.id.ivActivityIcon)

        fun bind(item: ActivityData) {
            tvDistance.text = item.distance
            tvDuration.text = item.duration
            tvActivityType.text = item.activityType
            tvTimeAgo.text = item.timeAgo

            // Установка иконки в зависимости от типа активности
            ivActivityIcon.setImageResource(ActivityIcons.getIconForActivityType(item.activityType))

            // Показать имя пользователя только если оно указано (для OthersActivityFragment)
            if (item.username.isNotEmpty()) {
                tvUsername.visibility = View.VISIBLE
                tvUsername.text = "@${item.username}"
            } else {
                tvUsername.visibility = View.GONE
            }

            // Добавляем обработчик нажатия на элемент
            itemView.setOnClickListener {
                onActivityClicked(item)
            }
        }
    }
}