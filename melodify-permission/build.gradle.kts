plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)

    alias(libs.plugins.maven.publish.plugin)
    alias(libs.plugins.nmcp)
}

android {
    namespace = "melodify.permission"
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
    implementation(project(":melodify-datastore"))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}


group = "com.github.MotiElitzur"
version = "1.77"

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.MotiElitzur"
            artifactId = "melodify-permission"
            version = "1.7"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
//
//publishing {
//    publications {
//        register<MavenPublication>("release") {
//            groupId = "melodify"
//            artifactId = "permission"
//            version = "1.0"
//            afterEvaluate {
//                from(components["release"])
//            }
//        }
//    }
//    repositories {
//        maven {
//            name = "GitHubPackages"
//            url = uri("https://maven.pkg.github.com/MotiElitzur/Melodify")
//            credentials {
//                username = (project.findProperty("gpr.user") as? String) ?: System.getenv("GITHUB_USERNAME")
//                password = (project.findProperty("gpr.token") as? String) ?: System.getenv("GITHUB_TOKEN")
//            }
//        }
//    }
//}