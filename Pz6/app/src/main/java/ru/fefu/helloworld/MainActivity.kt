package ru.fefu.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import ru.fefu.helloworld.model.ActivityTypeEnum
import ru.fefu.helloworld.viewmodel.ActivityViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        // Добавление тестовых данных, если список активностей пуст
        viewModel.allActivities.observe(this) { activities ->
            if (activities.isEmpty()) {
                createTestData()
            }
        }

        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_activity -> selectedFragment = ActivityFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it).commit()
            }
            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ActivityFragment()).commit()
        }
    }

    private fun createTestData() {
        // Создаем несколько тестовых активностей разных типов
        viewModel.createActivity(ActivityTypeEnum.RUNNING)
        viewModel.createActivity(ActivityTypeEnum.CYCLING)
        viewModel.createActivity(ActivityTypeEnum.WALKING)
    }
}