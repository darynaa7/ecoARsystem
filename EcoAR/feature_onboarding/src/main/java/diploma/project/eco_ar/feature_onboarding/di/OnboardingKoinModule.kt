package diploma.project.eco_ar.feature_onboarding.di

import diploma.project.eco_ar.feature_onboarding.data.OnboardingManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val OnboardingKoinModule = module {
    singleOf(::OnboardingManager)
}