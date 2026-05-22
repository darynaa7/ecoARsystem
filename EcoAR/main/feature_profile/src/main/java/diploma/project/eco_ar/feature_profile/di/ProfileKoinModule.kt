package diploma.project.eco_ar.feature_profile.di

import diploma.project.eco_ar.feature_profile.ui.viewModel.profile.ProfileViewModel
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditViewModel
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsViewModel
import diploma.project.eco_ar.feature_profile.ui.viewModel.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ProfileKoinModule = module {
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::ReportsViewModel)
    viewModelOf(::SettingsViewModel)
}