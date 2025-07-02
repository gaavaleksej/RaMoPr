package ru.fefu.helloworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.fefu.helloworld.R
import ru.fefu.helloworld.model.ActivityType

class ActivityTypeAdapter(
    private var items: List<ActivityType>,
    private val onItemClick: (ActivityType) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<ActivityType>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView as MaterialCardView
        private val tvActivityType: TextView = itemView.findViewById(R.id.tvActivityType)
        private val ivActivityIcon: ImageView = itemView.findViewById(R.id.ivActivityIcon)

        fun bind(item: ActivityType) {
            tvActivityType.text = item.name
            ivActivityIcon.setImageResource(item.iconResId)

            // Установка состояния выбранного элемента
            if (item.isSelected) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.purple_200))
                tvActivityType.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                tvActivityType.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.black))
            }

            // Обработчик нажатия на элемент
            itemView.setOnClickListener {
                // Снимаем выделение со всех элементов
                items.forEach { it.isSelected = false }

                // Выделяем текущий элемент
                item.isSelected = true

                // Уведомляем об изменении данных
                notifyDataSetChanged()

                // Вызываем обработчик нажатия
                onItemClick(item)
            }
        }
    }
}