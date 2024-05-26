plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.mealconnectuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mealconnectuser"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding=true
        dataBinding=true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.functions)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.database)
    implementation(libs.androidx.activity.v182)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //kotlin coroutines
    implementation(libs.kotlinx.coroutines.android)

    //room database

    val room_version = "2.6.1"

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.room.compiler)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)



    val nav_version = "2.7.7"

    // Kotlin
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Feature module Support
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    //firebase authentication
    implementation (libs.firebase.ui.auth)

    //firbase storage
    implementation("com.google.firebase:firebase-storage")

    //glide
    implementation (libs.glide)
    //easypermissions
    implementation (libs.easypermissions.ktx)

    //maps
    implementation (libs.play.services.location)
    implementation (libs.play.services.places)
    implementation (libs.places)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)

    //firebase messaging
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.installations)

    //razorpay
    implementation(libs.checkout)

}