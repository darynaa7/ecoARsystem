package diploma.project.eco_ar.feature_onboarding.domain

interface UserAuthChecker {
    suspend fun isUserAuthorized(): Boolean
}