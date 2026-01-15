plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.abc.myemployeeapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.abc.myemployeeapp"
        minSdk = 29
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // 🔹 RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // 🔹 SQLite KTX (optional, Kotlin-friendly)
    implementation("androidx.sqlite:sqlite-ktx:2.3.1")

    // 🔹 Glide (optional, for image loading)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



}