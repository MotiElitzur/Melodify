import com.android.utils.TraceUtils.simpleId

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "melodify.datastore"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

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
    implementation(project(":melodify-core"))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // dataStore
    implementation(libs.datastore.preferences)

    // Serialization
    implementation(libs.kotlin.serialization)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "melodify"
            artifactId = "datastore"
            version = "1.1"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MotiElitzur/Melodify")
            credentials {
                username = (project.findProperty("gpr.user") as? String) ?: System.getenv("GITHUB_USERNAME")
                password = (project.findProperty("gpr.token") as? String) ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}