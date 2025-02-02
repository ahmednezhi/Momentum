package com.ahmed_nezhi.database.converters

import androidx.room.TypeConverter
import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum

class Converters {

    @TypeConverter
    fun fromFrequencyEnum(frequency: FrequencyEnum): String {
        return frequency.name
    }

    @TypeConverter
    fun toFrequencyEnum(frequency: String): FrequencyEnum {
        return FrequencyEnum.valueOf(frequency)
    }

    @TypeConverter
    fun fromCategoryEnum(category: CategoryEnum): String {
        return category.name
    }

    @TypeConverter
    fun toCategoryEnum(category: String): CategoryEnum {
        return CategoryEnum.valueOf(category)
    }
}