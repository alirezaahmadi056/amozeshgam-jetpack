import java.util.Properties

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerPlugin)
    alias(libs.plugins.orgJetbrainsKotlinAndroid)
    alias(libs.plugins.com.google.gms)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.detektPlugin)
}

android {
    namespace = "com.amozeshgam.amozeshgam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.amozeshgam.amozeshgam"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.amozeshgam.amozeshgam.HiltAppRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        buildConfigField(
            "String",
            "GOOGLE_CREDENTIALS",
            "\"${properties.getProperty("GOOGLE_CREDENTIALS")}\""
        )
        buildConfigField("String", "BLACK_HOLE_API_KEY", "\"${properties.getProperty("BLACK_HOLE_API_KEY")}\"")
        buildConfigField("String", "CIPHER_KEY", "\"${properties.getProperty("CIPHER_KEY")}\"")

    }
    signingConfigs {
        create("release") {
            storeFile = rootProject.file("key.jks")
            storePassword = "kingofkingoffarhad22"
            keyPassword = "kingofkingoffarhad22"
            keyAlias = "key"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {
    implementation("com.securevale:rasp-android:0.6.0")
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.biometric.ktx)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.compose.ratingbar)
    implementation(libs.date.picker)
    implementation(libs.compose.shimmer)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.security.crypto)
    implementation(libs.firebase.analytics)
    implementation(platform(libs.firebase.bom))
    implementation(libs.paho.mqtt.android)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.hilt.android)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
    implementation(libs.coil.gif)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.glance.glance.appwidget)
    implementation(libs.androidx.glance.glance)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kdownloader)
    implementation(libs.googleid)
    "baselineProfile"(project(":baselineprofile"))
    ksp(libs.hilt.compiler)
    //-------------------------------------//
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    //------------------------------------//
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)
    //------------------------------------//
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.leakcanary.android)
}

