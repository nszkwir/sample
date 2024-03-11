@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
}

android {
    namespace = "com.spitzer.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.test.jvm)

    kapt(libs.hilt.compiler)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.junit)
    implementation(libs.hilt.android.testing)

    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.coroutines.guava)
    testImplementation(libs.kotlinx.coroutines.test.jvm)
    testImplementation(libs.hilt.android.testing)

    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.junit)
    implementation(kotlin("test"))

}