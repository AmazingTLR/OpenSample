import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

// Create settings override file if it does not exist or parse it
val localConfigFile = file("local.properties")
if (!localConfigFile.exists()) {
    println("No local config file, we create one from the template")
    file("local_template.properties").copyTo(localConfigFile)
}
val localConfig = Properties().apply { localConfigFile.reader().use(this::load) }

android {
    namespace = "com.amazingTLR.opensample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.amazingTLR.opensample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    defaultConfig {
        buildConfigField("String", "GITHUB_API_KEY", "${localConfig["GITHUB_API_KEY"]}")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)


    //Ktor
    implementation(libs.ktor.client.android)

    //Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    // Internal libs
    implementation(project(":usecase"))
    implementation(project(":network:ktor"))

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Debug
    debugImplementation(libs.androidx.ui.tooling)
}