package diploma.project.eco_ar.feature_map.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import diploma.project.eco_ar.core.ui.miscellaneous.PlacesInitializer
import diploma.project.eco_ar.feature_map.R
import diploma.project.eco_ar.feature_map.ui.viewModel.MapViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MapKoinModule = module {
    single<PlacesInitializer> {
        object : PlacesInitializer {
            override fun init(context: Context) {
                if (!Places.isInitialized()) {
                    Places.initializeWithNewPlacesApiEnabled(
                        context,
                        context.getString(R.string.google_maps_api_key)
                    )
                }
            }
        }
    }
    single<PlacesClient> { Places.createClient(androidContext()) }

    viewModelOf(::MapViewModel)
}