plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.financiaplus.app"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.financiaplus.app"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
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
    buildFeatures {
        compose = true
        buildConfig = true
    }

    // TODO: move sensible data to local.properties before production
    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "AML_BASE_URL", "\"http://10.0.2.2:8081/\"")
            buildConfigField("String", "BANK_BASE_URL", "\"http://10.0.2.2:8082/\"")
            buildConfigField("String", "GEO_BASE_URL", "\"http://ip-api.com/\"")
            buildConfigField("String", "AML_API_KEY", "\"dev-aml-key\"")
            buildConfigField("String", "BANK_API_KEY", "\"dev-bank-key\"")
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "AML_BASE_URL", "")
            buildConfigField("String", "BANK_BASE_URL", "")
            buildConfigField("String", "GEO_BASE_URL", "")
            buildConfigField("String", "AML_API_KEY", "")
            buildConfigField("String", "BANK_API_KEY", "")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Hilt
    implementation(libs.hilt.android.v256)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler.v256)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Security
    implementation(libs.androidx.security.crypto)

    // Material Icons Extended
    implementation(libs.androidx.compose.material.icons.extended)
}