package ru.fefu.helloworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.fefu.helloworld.adapter.ActivityAdapter
import ru.fefu.helloworld.model.ActivityData
import ru.fefu.helloworld.util.ActivityDataHelper


class MyActivityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivityAdapter
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_activity, container, false)
        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewMyActivities)
        recyclerView.layoutManager = LinearLayoutManager(context)
        // Создание и установка адаптера с обработчиком нажатия
        adapter = ActivityAdapter(
            ActivityDataHelper.getMyActivities(),
            onActivityClicked = { activityData -> openActivityDetail(activityData) }
        )
        recyclerView.adapter = adapter
        // Настройка FAB
        fab = view.findViewById(R.id.fabAddActivity)
        fab.setOnClickListener {
            Toast.makeText(context, "Добавление новой активности", Toast.LENGTH_SHORT).show()
        }
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