package diploma.project.eco_ar.feature_statistics.di

import diploma.project.eco_ar.feature_statistics.ui.viewModel.StatisticsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val StatisticsKoinModule = module {
    viewModelOf(::StatisticsViewModel)
}