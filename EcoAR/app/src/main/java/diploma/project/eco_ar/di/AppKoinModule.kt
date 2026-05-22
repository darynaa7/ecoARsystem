package diploma.project.eco_ar.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import diploma.project.eco_ar.core.data.UserOutLogger
import diploma.project.eco_ar.core.data.repository.UserDataRepository
import diploma.project.eco_ar.core.domain.model.userData.UserData
import diploma.project.eco_ar.core.ui.miscellaneous.ActivityOpener
import diploma.project.eco_ar.feature_auth.data.repository.AuthRepository
import diploma.project.eco_ar.feature_onboarding.domain.UserAuthChecker
import diploma.project.eco_ar.ui.activities.MainActivity
import kotlinx.coroutines.flow.StateFlow
import org.koin.dsl.module
import java.io.File

val AppKoinModule = module {
    single<UserAuthChecker> {
        val authRepository = get<AuthRepository>()

        object : UserAuthChecker {
            override suspend fun isUserAuthorized(): Boolean {
                val refreshResult = authRepository.checkAndRefresh()

                return if (refreshResult.isSuccess) {
                    authRepository.savedUserDataStore.now() != null
                } else {
                    false
                }
            }
        }
    }

    single<UserDataRepository> {
        val authRepository = get<AuthRepository>()

        object : UserDataRepository {
            override suspend fun getToken(): String? {
                return authRepository.savedUserDataStore.now()?.accessToken
            }

            override suspend fun getCurrentSessionUserData(): StateFlow<UserData?> {
                return authRepository.currentSessionUserData
            }

            override suspend fun setCurrentSessionUserData(username: String, email: String, password: String?): Result<Unit> {
                return authRepository.update(
                    username = username,
                    email = email,
                    password = password
                )
            }

            override suspend fun getProfilePicture(): Result<File> {
                return authRepository.getProfilePicture()
            }

            override suspend fun updateProfilePicture(file: File): Result<Unit> {
                return authRepository.updateProfilePicture(file)
            }

            override suspend fun deleteSavedUserData() {
                authRepository.savedUserDataStore.set(null)
            }
        }
    }

    single<UserOutLogger> {
        val authRepository = get<AuthRepository>()

        object : UserOutLogger {
            override suspend fun logout(): Result<Unit> {
                return authRepository.logout()
            }
        }
    }

    single<ActivityOpener> {
        object : ActivityOpener {
            override fun openMainActivity(context: Context) {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

                context.startActivity(intent)
            }

            override fun pendingIntentForMainActivity(context: Context): PendingIntent {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

                return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }
        }
    }
}