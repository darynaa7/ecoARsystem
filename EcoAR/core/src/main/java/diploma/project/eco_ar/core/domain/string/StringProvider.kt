package diploma.project.eco_ar.core.domain.string

import android.content.Context

class StringProvider(private val context: Context) {
    fun provide(resId: Int): String {
        return context.getString(resId)
    }

    fun <T> provideAsFailedResult(resId: Int): Result<T> {
        return Result.failure(Exception(context.getString(resId)))
    }
}