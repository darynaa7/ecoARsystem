package diploma.project.eco_ar.core.data

interface UserOutLogger {
    suspend fun logout(): Result<Unit>
}