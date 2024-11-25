import com.android.build.api.dsl.ApplicationDefaultConfig
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.8.0"
}

val properties = Properties().apply {
    load(FileInputStream(rootProject.file("apikey.properties")))
}

fun ApplicationDefaultConfig.addManifestPlaceholdersAndBuildConfig(key: String) {
    (properties[key] as? String)?.let {
        manifestPlaceholders[key] = it
        buildConfigField("String", key, it)
    }
}

android {
    namespace = "com.example.umc_wireframe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.umc_wireframe"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        addManifestPlaceholdersAndBuildConfig("SHORT_TERM_FORECAST_KEY")
        addManifestPlaceholdersAndBuildConfig("MID_TERM_FORECAST_KEY")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //viewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.fragment:fragment-ktx:1.7.1")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")

    //navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)

    // coroutine 관련 의존성
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    //CameraX
    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.0.0-alpha28")

    //Worker
    implementation("androidx.work:work-runtime-ktx:2.8.0")
}