package com.ahmed_nezhi.common.enums

enum class FrequencyEnum {
    DAILY,
    EACH_TWO_DAYS,
    ONCE_A_WEEK,
    ONCE_A_MONTH,
}


fun FrequencyEnum.toHumanReadableFrequency(): String {
    return this.name.replace("_", " ")
        .split(" ")
        .joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
}
