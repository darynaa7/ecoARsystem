package diploma.project.eco_ar.feature_auth.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import diploma.project.eco_ar.core.data.common.dataStore.ObjectDataStoreValue
import diploma.project.eco_ar.core.data.common.errors.parseAsError
import diploma.project.eco_ar.core.domain.miscellaneous.authFailure
import diploma.project.eco_ar.core.domain.miscellaneous.emptySuccess
import diploma.project.eco_ar.core.domain.model.userData.UserData
import diploma.project.eco_ar.core.domain.serialization.AppJson
import diploma.project.eco_ar.core.domain.string.StringProvider
import diploma.project.eco_ar.feature_auth.data.mappers.UserDataMapper
import diploma.project.eco_ar.feature_auth.data.remote.dto.requests.LoginRequestDTO
import diploma.project.eco_ar.feature_auth.data.remote.dto.requests.RefreshRequestDTO
import diploma.project.eco_ar.feature_auth.data.remote.dto.requests.RegistrationRequestDTO
import diploma.project.eco_ar.feature_auth.data.remote.dto.requests.UpdateUserRequestDTO
import diploma.project.eco_ar.feature_auth.data.remote.dto.responses.AuthResponseDTO
import diploma.project.eco_ar.feature_auth.data.remote.dto.responses.TokensAuthResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AuthRepository(
    private val context: Context,
    dataStore: DataStore<Preferences>,
    private val httpClient: HttpClient,
    private val stringProvider: StringProvider,
    private val userDataMapper: UserDataMapper
) {

    val savedUserDataStore = ObjectDataStoreValue<UserData?>(
        dataStore = dataStore,
        defaultValue = null,
        keyName = "saved_user_data_key",
        setter = { value -> AppJson.encodeToString(value) },
        getter = { string -> string?.let { AppJson.decodeFromString(it) } }
    )

    var currentSessionUserData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    var currentSessionProfilePicture: MutableStateFlow<File?> = MutableStateFlow(null)

    suspend fun clearSavedAndSessionUserData() {
        currentSessionUserData.value = null
        currentSessionProfilePicture.value = null
        savedUserDataStore.set(null)
    }

    // Common

    suspend fun register(username: String, email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RegistrationRequestDTO(
                    username = username,
                    email = email,
                    password = password
                )

                val response = httpClient.post("/auth/register") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }

                if (response.status == HttpStatusCode.OK) {
                    val tokensAuthResponseDTO = response.body<TokensAuthResponseDTO>()
                    val userData = userDataMapper.dtoToModel(tokensAuthResponseDTO.userData, tokensAuthResponseDTO.accessToken, tokensAuthResponseDTO.refreshToken)

                    currentSessionUserData.value = userData
                    savedUserDataStore.set(userData)

                    Result.emptySuccess()
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun login(username: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequestDTO(
                    username = username,
                    password = password
                )

                val response = httpClient.post("/auth/login") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }

                if (response.status == HttpStatusCode.OK) {
                    val tokensAuthResponseDTO = response.body<TokensAuthResponseDTO>()
                    val userData = userDataMapper.dtoToModel(tokensAuthResponseDTO.userData, tokensAuthResponseDTO.accessToken, tokensAuthResponseDTO.refreshToken)

                    currentSessionUserData.value = userData
                    savedUserDataStore.set(userData)

                    launch {
                        getProfilePicture()
                    }

                    Result.emptySuccess()
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun logout(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val userData = savedUserDataStore.now() ?: return@withContext Result.emptySuccess()

                httpClient.post("/auth/logout") {
                    contentType(ContentType.Application.Json)
                    bearerAuth(userData.accessToken)
                }

                Result.emptySuccess()
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun checkAndRefresh(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val savedUserData = savedUserDataStore.now() ?: return@withContext Result.authFailure(stringProvider)

                val response = httpClient.get("/auth/check") {
                    contentType(ContentType.Application.Json)
                    bearerAuth(savedUserData.accessToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    val authResponseDTO = response.body<AuthResponseDTO>()

                    val request = RefreshRequestDTO(refreshToken = savedUserData.refreshToken)

                    val response = httpClient.post("/auth/refresh") {
                        contentType(ContentType.Application.Json)
                        setBody(request)
                    }

                    if (response.status == HttpStatusCode.OK) {
                        val tokensAuthResponseDTO = response.body<TokensAuthResponseDTO>()
                        val userData = userDataMapper.dtoToModel(authResponseDTO.userData, tokensAuthResponseDTO.accessToken, tokensAuthResponseDTO.refreshToken)

                        currentSessionUserData.value = userData
                        savedUserDataStore.set(userData)

                        Result.emptySuccess()
                    } else {
                        response.parseAsError()
                    }
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun update(username: String, email: String, password: String?): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val savedUserData = savedUserDataStore.now() ?: return@withContext Result.authFailure(stringProvider)

                val request = UpdateUserRequestDTO(
                    username = username,
                    email = email,
                    password = password
                )

                val response = httpClient.post("/auth/updateUserData") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                    bearerAuth(savedUserData.accessToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    val newUserData = savedUserData.copy(
                        username = username,
                        email = email
                    )

                    currentSessionUserData.value = newUserData
                    savedUserDataStore.set(newUserData)

                    Result.emptySuccess()
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun getProfilePicture(): Result<File> {
        currentSessionProfilePicture.value?.let { return Result.success(it) }

        return withContext(Dispatchers.IO) {
            try {
                val savedUserData = savedUserDataStore.now() ?: return@withContext Result.authFailure(stringProvider)

                val response = httpClient.get("/file/profile") {
                    bearerAuth(savedUserData.accessToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    val tempFile = File.createTempFile("profile_picture", ".tmp", context.cacheDir)
                    val inputStream = response.bodyAsChannel().toInputStream()

                    tempFile.outputStream().use { output ->
                        inputStream.copyTo(output)
                    }

                    currentSessionProfilePicture.value = tempFile

                    Result.success(tempFile)
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun updateProfilePicture(file: File): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val savedUserData = savedUserDataStore.now() ?: return@withContext Result.authFailure(stringProvider)

                val response = httpClient.submitFormWithBinaryData(
                    url = "/file/profile",
                    formData = formData {
                        append(
                            key = "file",
                            value = file.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                                append(HttpHeaders.ContentType, "image/jpeg")
                            }
                        )
                    }
                ) {
                    bearerAuth(savedUserData.accessToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    currentSessionProfilePicture.value = file

                    Result.emptySuccess()
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }
}