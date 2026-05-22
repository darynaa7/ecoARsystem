package diploma.project.eco_ar.feature_ar.di

import diploma.project.eco_ar.feature_ar.ui.viewModel.ARViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ARKoinModule = module {
    viewModelOf(::ARViewModel)
}