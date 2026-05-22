package diploma.project.eco_ar.feature_home.di

import diploma.project.eco_ar.feature_home.ui.viewModel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val HomeKoinModule = module {
    viewModelOf(::HomeViewModel)
}