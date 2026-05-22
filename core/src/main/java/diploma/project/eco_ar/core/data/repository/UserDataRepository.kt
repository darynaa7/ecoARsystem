package diploma.project.eco_ar.core.data.repository

import diploma.project.eco_ar.core.domain.model.userData.UserData
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface UserDataRepository {
    suspend fun getToken(): String?

    suspend fun getCurrentSessionUserData(): StateFlow<UserData?>
    suspend fun setCurrentSessionUserData(username: String, email: String, password: String?): Result<Unit>
    suspend fun getProfilePicture(): Result<File>
    suspend fun updateProfilePicture(file: File): Result<Unit>
    suspend fun deleteSavedUserData()
}