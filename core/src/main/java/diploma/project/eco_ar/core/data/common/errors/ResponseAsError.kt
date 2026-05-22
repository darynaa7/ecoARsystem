package diploma.project.eco_ar.core.data.common.errors

import diploma.project.eco_ar.core.data.remote.dto.ErrorDTO
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend fun <T> HttpResponse.parseAsError(): Result<T> {
    return try {
        val errorDTO = body<ErrorDTO>()

        Result.failure(Exception(errorDTO.message ?: status.toString()))
    } catch (e: Exception) {
        e.printStackTrace()

        Result.failure(Exception(status.toString()))
    }
}