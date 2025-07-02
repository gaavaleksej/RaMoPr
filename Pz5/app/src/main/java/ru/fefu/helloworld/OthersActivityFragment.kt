package ru.fefu.helloworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.adapter.ActivityAdapter
import ru.fefu.helloworld.model.ActivityData
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
        // Создание и установка адаптера с обработчиком нажатия
        adapter = ActivityAdapter(
            ActivityDataHelper.getOthersActivities(),
            onActivityClicked = { activityData -> openActivityDetail(activityData) }
        )
        recyclerView.adapter = adapter
        
        return view
    }
    // Метод для открытия экрана детализации активности
    private fun openActivityDetail(activityData: ActivityData) {
        context?.let {
            val intent = ActivityDetailActivity.newIntent(it, activityData)
            startActivity(intent)
        }
    }
}