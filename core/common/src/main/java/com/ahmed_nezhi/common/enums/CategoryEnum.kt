package com.ahmed_nezhi.common.enums

import com.ahmed_nezhi.common.R

enum class CategoryEnum(val imageResId: Int, val bgColorResId: Int) {
    FITNESS(R.drawable.ic_fitness, R.color.soft_mint_green),
    HEALTH(R.drawable.ic_health, R.color.soft_lavender),
    WORK(R.drawable.ic_work, R.color.soft_peach),
    PERSONAL(R.drawable.ic_personal, R.color.soft_sky_blue)
}

fun getCategoryList(): List<String> {
    return CategoryEnum.entries.map { it.name }
}

fun String.toHumanReadableCategory(): String {
    return this.lowercase()
        .split("_")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}