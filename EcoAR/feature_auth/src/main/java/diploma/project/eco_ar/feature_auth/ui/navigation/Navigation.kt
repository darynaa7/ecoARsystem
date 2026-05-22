package diploma.project.eco_ar.feature_auth.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import diploma.project.eco_ar.core.ui.miscellaneous.ListenToEvents
import diploma.project.eco_ar.core.ui.miscellaneous.LoaderController
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.AuthRoutes
import diploma.project.eco_ar.core.ui.navigation.routes.MainRoutes
import diploma.project.eco_ar.feature_auth.data.repository.AuthRepository
import diploma.project.eco_ar.feature_auth.ui.screens.SignInScreen
import diploma.project.eco_ar.feature_auth.ui.screens.SignUpScreen
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInEvent
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInUiAction
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInViewModel
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpEvent
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpUiAction
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

fun EntryProviderScope<NavKey>.addAuthRoutes(backStack: NestedRoutesBackStack) {
    entry<AuthRoutes.SignIn> {
        val authRepository = koinInject<AuthRepository>()

        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        ListenToEvents(viewModel.events) { event, _, onError ->
            when (event) {
                is SignInEvent.SignInSuccess -> backStack.clearAndPush(MainRoutes.RouteOne)
                is SignInEvent.SignInFailure -> onError(event.message)
            }
        }

        LoaderController(uiState.isLoading)

        LaunchedEffect(Unit) {
            delay(1000)
            authRepository.clearSavedAndSessionUserData()
        }

        SignInScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
            onUiAction = { action ->
                when (action) {
                    SignInUiAction.NavigateToSignUp -> backStack.push(AuthRoutes.SignUp)
                }
            }
        )
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    entry<AuthRoutes.SignUp> {
        val signUpViewModel = koinViewModel<SignUpViewModel>()
        val signUpUiState by signUpViewModel.uiState.collectAsStateWithLifecycle()

        ListenToEvents(signUpViewModel.events) { event, _, onError ->
            when (event) {
                SignUpEvent.SignUpSuccess -> backStack.clearAndPush(MainRoutes.RouteOne)
                is SignUpEvent.SignUpFailure -> onError(event.message)
            }
        }

        LoaderController(signUpUiState.isLoading)

        SignUpScreen(
            uiState = signUpUiState,
            onAction = signUpViewModel::onAction,
            onUiAction = { action ->
                when (action) {
                    SignUpUiAction.NavigateBack -> backStack.pop()
                }
            }
        )
    }
}