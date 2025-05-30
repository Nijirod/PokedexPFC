import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.51.1"
}

android {
    namespace = "com.example.pokedexapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pokedexapp"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt{
            arguments{
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.coil.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    implementation(libs.timber)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.navigation.compose.v277)
    implementation(libs.androidx.material)
    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
    kapt(libs.androidx.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.accompanist.insets)
    implementation (libs.snapper)
    implementation ("dev.chrisbanes.snapper:snapper:0.3.0")
    kapt("androidx.room:room-compiler:2.7.0")
    implementation( libs.androidx.room.ktx)
    implementation (libs.kotlin.reflect)
    implementation(libs.play.services.wearable)
    implementation(libs.gms.play.services.wearable)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
}