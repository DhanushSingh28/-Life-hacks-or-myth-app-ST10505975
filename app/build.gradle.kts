plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}
//I change API level 37 from 36 to account for the changes made in compileSdk.
//I also removed th view binding as no longer need i my main.activity.
android {
    namespace = "com.example.studyandverifylifehackswithflashcardsst10505975"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.studyandverifylifehackswithflashcardsst10505975"
        minSdk = 27
        targetSdk = 37
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

    buildFeatures {
        compose = true
    }
}
//when it came to my build.gradle.kts I had an issue with the kts recognising the libs.versions.toml.
// my version of android studios was recognizing the libs in libraries so required the help from gemini.ai to fix the issue
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
}
