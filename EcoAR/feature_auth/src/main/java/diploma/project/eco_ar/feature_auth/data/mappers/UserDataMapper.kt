package diploma.project.eco_ar.feature_auth.data.mappers

import diploma.project.eco_ar.core.domain.model.userData.UserData
import diploma.project.eco_ar.feature_auth.data.remote.dto.UserDataDTO
import kotlin.uuid.ExperimentalUuidApi

class UserDataMapper {
    @OptIn(ExperimentalUuidApi::class)
    fun dtoToModel(dto: UserDataDTO, accessToken: String, refreshToken: String): UserData {
        return UserData(
            uuid = dto.id,
            accessToken = accessToken,
            refreshToken = refreshToken,
            username = dto.username,
            email = dto.email
        )
    }
}