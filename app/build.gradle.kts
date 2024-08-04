plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.somadhan.sopkenenglish"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.somadhan.sopkenenglish"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("dagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
                arg("dagger.hilt.android.internal.projectType", "APP")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.hilt.android.v249)
    implementation(libs.runtime.livedata)
    implementation(libs.material3.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose.v100)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v114)
    androidTestImplementation(libs.espresso.core.v350)
    implementation(libs.navigation.compose.v251)
}
