package diploma.project.eco_ar.feature_auth.di

import diploma.project.eco_ar.feature_auth.data.mappers.UserDataMapper
import diploma.project.eco_ar.feature_auth.data.repository.AuthRepository
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInViewModel
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val AuthKoinModule = module {
    singleOf(::AuthRepository)

    singleOf(::UserDataMapper)

    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
}