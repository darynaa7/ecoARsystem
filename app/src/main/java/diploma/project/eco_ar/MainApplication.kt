package diploma.project.eco_ar

import android.app.Application
import diploma.project.eco_ar.core.di.CoreKoinModule
import diploma.project.eco_ar.core.ui.miscellaneous.PlacesInitializer
import diploma.project.eco_ar.di.AppKoinModule
import diploma.project.eco_ar.feature_auth.di.AuthKoinModule
import diploma.project.eco_ar.feature_onboarding.di.OnboardingKoinModule
import diploma.project.eco_ar.main.di.MainKoinModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                CoreKoinModule,
                AppKoinModule,
                AuthKoinModule,
                MainKoinModule,
                OnboardingKoinModule
            )
        }

        val placesInitializer by inject<PlacesInitializer>()
        placesInitializer.init(this)
    }
}