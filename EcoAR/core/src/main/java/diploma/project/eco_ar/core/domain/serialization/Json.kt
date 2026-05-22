package diploma.project.eco_ar.core.domain.serialization

import kotlinx.serialization.json.Json

val AppJson = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    explicitNulls = true
    prettyPrint = true
}