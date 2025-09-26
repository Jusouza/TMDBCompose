import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.devspacecinenow"
    compileSdk = 34

    android.buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.devspacecinenow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val localProperties = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(localProperties.inputStream())

        val apiKey = properties.getProperty("API_KEY")

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.navigation.compose)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.retrofit)

    implementation(libs.serialization.json)
    implementation(libs.coil.compose)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.bundles.testing)
    debugImplementation(libs.bundles.debug)
}