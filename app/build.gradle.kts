import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Keystore file: eco_ar_keystore.jks
// Password: eco_ar_diploma_project_2026
// Key: eco_ar_key
// Password: eco_ar_diploma_project_2026

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

android {
    namespace = "diploma.project.eco_ar"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "diploma.project.eco_ar"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

subprojects {
    plugins.withType(com.android.build.gradle.api.AndroidBasePlugin::class.java) {
        dependencies {
            "testImplementation"(libs.junit)
            "androidTestImplementation"(libs.androidx.junit)
            "androidTestImplementation"(libs.androidx.espresso.core)
            "androidTestImplementation"(platform(libs.androidx.compose.bom))
            "androidTestImplementation"(libs.androidx.ui.test.junit4)
            "debugImplementation"(libs.androidx.ui.tooling)
            "debugImplementation"(libs.androidx.ui.test.manifest)
        }
    }
}

dependencies {
    // Modules
    implementation(project(":core"))
    implementation(project(":feature_auth"))
    implementation(project(":feature_onboarding"))
    implementation(project(":main"))

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}