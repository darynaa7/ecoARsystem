package diploma.project.eco_ar.main.di

import diploma.project.eco_ar.feature_ar.di.ARKoinModule
import diploma.project.eco_ar.feature_home.di.HomeKoinModule
import diploma.project.eco_ar.feature_map.di.MapKoinModule
import diploma.project.eco_ar.feature_profile.di.ProfileKoinModule
import diploma.project.eco_ar.feature_statistics.di.StatisticsKoinModule
import org.koin.dsl.module

val MainKoinModule = module {
    includes(
        ARKoinModule,
        HomeKoinModule,
        MapKoinModule,
        ProfileKoinModule,
        StatisticsKoinModule
    )
}