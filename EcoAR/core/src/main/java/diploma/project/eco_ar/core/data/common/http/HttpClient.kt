package diploma.project.eco_ar.core.data.common.http

import android.util.Log
import diploma.project.eco_ar.core.domain.serialization.AppJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json

const val BASE_API_URL = "ecoarserver-12.onrender.com"

val HttpClient = HttpClient(Android) {
    install(DefaultRequest) {
        url.protocol = URLProtocol.HTTPS
        host = BASE_API_URL
    }
    install(ContentNegotiation) {
        json(AppJson)
    }
    install(Logging) {
        level = LogLevel.ALL

        this.logger = object : Logger {
            override fun log(message: String) {
                Log.d("TAG HTTP", message)
            }
        }
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 10_000
        connectTimeoutMillis = 10_000
        socketTimeoutMillis = 100_000
    }
    install(HttpRequestRetry) {
        maxRetries = 1
        delayMillis { 2_000L }
    }
}