package diploma.project.eco_ar.core.domain.miscellaneous

import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.domain.string.StringProvider

fun Result.Companion.emptySuccess(): Result<Unit> {
    return Result.success(Unit)
}

fun <T> Result.Companion.authFailure(stringProvider: StringProvider): Result<T> {
    return stringProvider.provideAsFailedResult(R.string.user_is_not_authenticated)
}