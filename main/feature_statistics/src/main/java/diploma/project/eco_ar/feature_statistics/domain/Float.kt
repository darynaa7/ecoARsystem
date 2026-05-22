package diploma.project.eco_ar.feature_statistics.domain

import java.util.Locale

fun Float.format(): String {
    return if (this % 1f == 0f) {
        toInt().toString()
    } else {
        String.format(Locale.ENGLISH, "%.2f", this)
    }
}