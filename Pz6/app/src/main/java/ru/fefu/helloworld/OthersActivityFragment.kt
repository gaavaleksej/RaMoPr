package ru.fefu.helloworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.adapter.ActivityAdapter
import ru.fefu.helloworld.model.ActivityData
import ru.fefu.helloworld.model.ActivityDataModel
import ru.fefu.helloworld.util.ActivityDataHelper

class OthersActivityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_others_activity, container, false)
        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewOthersActivities)
        recyclerView.layoutManager = LinearLayoutManager(context)
        // Получение тестовых данных о активностях других пользователей
        val othersActivities = ActivityDataHelper.getOthersActivities()
        // Создание и установка адаптера с обработчиком нажатия
        adapter = ActivityAdapter(othersActivities) { activityDataModel -> 
            openActivityDetail(activityDataModel) 
        }
        recyclerView.adapter = adapter
        
        return view
    }
    
    // Метод для открытия экрана детализации активности
    private fun openActivityDetail(activityDataModel: ActivityDataModel) {
        try {
            // Преобразуем ActivityDataModel в ActivityData
            val activityData = ActivityData(
                distance = activityDataModel.distance,
                duration = activityDataModel.duration,
                activityType = activityDataModel.activityType,
                timeAgo = activityDataModel.timeAgo,
                date = "Сегодня", // Подставляем примерную дату
                username = "", // Не используется для отображения в деталях
                startTime = activityDataModel.startTime,
                finishTime = activityDataModel.finishTime
            )
            val intent = ActivityDetailActivity.newIntent(requireContext(), activityData)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Ошибка при открытии деталей: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}