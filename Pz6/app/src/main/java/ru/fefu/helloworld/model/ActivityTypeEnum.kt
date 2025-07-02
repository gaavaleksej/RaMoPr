package ru.fefu.helloworld.model

import ru.fefu.helloworld.R

enum class ActivityTypeEnum(val stringId: String, val iconResId: Int) {
    CYCLING("Велосипед", R.drawable.ic_bike),
    RUNNING("Бег", R.drawable.ic_run),
    WALKING("Шаг", R.drawable.ic_walk);

    companion object {
        fun fromString(value: String): ActivityTypeEnum {
            return values().find { it.stringId == value } ?: WALKING
        }
    }
} 