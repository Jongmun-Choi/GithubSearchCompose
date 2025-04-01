plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.dave.githubsearchcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dave.githubsearchcompose"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isShrinkResources = false
            isMinifyEnabled = false

            buildConfigField("String", "AUTH_URL", "\"https://github.com\"")
            buildConfigField("String", "API_URL", "\"https://api.github.com\"")
            buildConfigField("String","clientId", "\"Ov23lic4V0Q63ZTvg1j4\"")
            buildConfigField("String", "clientSecret", "\"8310b2ef65d919da55704a87b32681fe2952fe9b\"")
            buildConfigField("String", "Shared_Preference_Name", "\"TokenInfo\"")
        }

        release {
            isMinifyEnabled = false
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.tools.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.splashscreen)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // di - hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // retrofit2
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // moshi
    implementation(libs.moshi.converter)
    ksp(libs.moshi.kotlin.codegen)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

}