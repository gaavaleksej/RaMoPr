package ru.fefu.helloworld.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.R
import ru.fefu.helloworld.adapter.ActivityHistoryAdapter
import ru.fefu.helloworld.viewmodel.ActivityViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var viewModel: ActivityViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        
        recyclerView = findViewById(R.id.recycler_activity_history)
        emptyView = findViewById(R.id.empty_view)
        
        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        
        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Создание адаптера с обработчиком нажатий
        val adapter = ActivityHistoryAdapter(0) { activity ->
            // Обработка нажатия на элемент списка
            // Здесь можно добавить открытие детальной информации об активности
        }
        
        recyclerView.adapter = adapter
        
        // Подписка на обновления списка активностей
        viewModel.allActivities.observe(this) { activities ->
            if (activities.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                adapter.setActivities(activities)
            }
        }
    }
} 