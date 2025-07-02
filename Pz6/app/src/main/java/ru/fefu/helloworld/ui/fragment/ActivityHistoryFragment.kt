package ru.fefu.helloworld.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.R
import ru.fefu.helloworld.adapter.ActivityHistoryAdapter
import ru.fefu.helloworld.model.ActivityEntity
import ru.fefu.helloworld.viewmodel.ActivityViewModel

class ActivityHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var viewModel: ActivityViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity_history, container, false)
        
        recyclerView = view.findViewById(R.id.recycler_activity_history)
        emptyView = view.findViewById(R.id.empty_view)
        
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Инициализация ViewModel
        viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        
        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        // Создание адаптера с обработчиком нажатий
        val adapter = ActivityHistoryAdapter(0) { activity ->
            onActivityItemClick(activity)
        }
        
        recyclerView.adapter = adapter
        
        // Подписка на обновления списка активностей
        viewModel.allActivities.observe(viewLifecycleOwner) { activities ->
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
    
    private fun onActivityItemClick(activity: ActivityEntity) {
        // Обработка нажатия на элемент списка
        // Здесь можно добавить открытие детальной информации об активности
    }
    
    companion object {
        fun newInstance() = ActivityHistoryFragment()
    }
} 