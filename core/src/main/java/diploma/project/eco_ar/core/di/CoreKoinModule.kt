package diploma.project.eco_ar.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import diploma.project.eco_ar.core.data.common.http.HttpClient
import diploma.project.eco_ar.core.data.mappers.ReportMapper
import diploma.project.eco_ar.core.data.repository.ReportsRepository
import diploma.project.eco_ar.core.data.repository.WeatherDataRepository
import diploma.project.eco_ar.core.domain.string.StringProvider
import diploma.project.eco_ar.core.domain.validation.ValidationChecker
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "EcoARAppDataStorePreferences")

val CoreKoinModule = module {
    single { androidContext().dataStore }

    single<HttpClient> { HttpClient }

    singleOf(::ReportsRepository)
    singleOf(::WeatherDataRepository)

    singleOf(::StringProvider)
    singleOf(::ValidationChecker)

    singleOf(::ReportMapper)
}