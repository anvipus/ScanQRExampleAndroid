plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.navigation.plugin)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.anvipus.core"
    compileSdk = Integer.parseInt(libs.versions.compile.get())

    defaultConfig {
        minSdk = Integer.parseInt(libs.versions.minimum.get())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            val KUNCI_GARAM: String by project
            isMinifyEnabled = false
            buildConfigField("String", "KUNCI_GARAM", KUNCI_GARAM)
            resValue("string", "app_name_config", "DEV Android Explore")
        }
        release {
            val KUNCI_GARAM: String by project
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "KUNCI_GARAM", KUNCI_GARAM)
            resValue("string", "app_name_config", "Android Explore")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    dataBinding {
        enable = true
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES.txt"
            excludes += "/META-INF/LICENSE.txt"
            excludes += "/META-INF/NOTICE.txt"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/license.txt"
        }
    }
}

dependencies {
    //android sdk
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.common.java8)
    kapt(libs.androidx.lifecycle.common.java8)
    kapt(libs.lifecycle.common.java8.kapt)
    api(libs.androidx.appcompat)
    api(libs.androidx.worker)
    api(libs.androidx.paging)
    api(libs.androidx.security.crypto)
    api(libs.material)
    api(libs.navigation.ui.ktx)
    api(libs.navigation.runtime.ktx)
    api(libs.navigation.fragment.ktx)
    api(libs.recycler.view)
    api(libs.viewpager)
    api(libs.androidx.constraint)
    api(libs.kotlinx.coroutines.android)

    // firebase
    api(platform(libs.firebase.bom))
    api(libs.firebase.crashlytics.ktx)
    api(libs.firebase.analytics.ktx)
    api(libs.firebase.messaging.ktx)
    api(libs.firebase.perf.ktx)
    api(libs.firebase.appcheck.playintegrity)
    api(libs.firebase.appcheck.debug)
    api(libs.firebase.appcheck)

    //compose

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.activity.compose)
    api(libs.androidx.material3)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.ui)
    api(libs.androidx.compose.material)
    api(libs.androidx.compose.material.icon)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.constraint.compose)
    api(libs.navigation.compose)
    api(libs.accompanist.flowlayout)
    api(libs.accompanist.permissions)
    api(libs.accompanist.systemuicontroller)
    api(libs.lottie.compose)
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.coil.compose)
    api(libs.androidx.foundation)


    //retrofit
    api(libs.retrofit)
    api(libs.retrofit.converter.moshi)
    api(libs.converter.gson)

    //moshi
    api(libs.moshi)
    api(libs.moshi.codegen)
    kapt(libs.moshi.kotlin.codegen.kapt)

    // okhttp
    api(platform(libs.okhttp))
    api(libs.okhttp.module)
    api(libs.okhttp.logging.interceptor)

    //glide
    api(libs.glide)
    api(libs.glide.okhttp3)
    kapt(libs.glide.compiler.kapt)

    //gson
    api(libs.gson)

    //dagger
    api(libs.dagger)
    api(libs.dagger.android)
    api(libs.dagger.android.support)
    api(libs.dagger.hilt)
    kapt(libs.dagger.hilt.kapt)

    //room
    api(libs.room)

    //jwt
    api(libs.jwt.decode)

    //biometric
    api(libs.biometric)

    //lottie
    api(libs.lottie)

    //shimmer
    api(libs.shimmer)

    //camerax
    api(libs.camera.core)
    api(libs.camera.camera2)
    api(libs.camera.lifecycle)
    api(libs.camera.view)

    //ml kit barcode scanning
    api(libs.ml.kit.barcode.scanning)

    //ml kit text recognition
    api(libs.play.services.mlkit.text.recognition)
    api(libs.mlkit.detection)

    //timber
    api(libs.timber)

    //zxing
    api(libs.zxing)
    api(libs.journey.zxing)

    //gpuimage
    api(libs.gpuimage)

    //exifinterface
    api(libs.exifinterface)


    //play service
    api(libs.play.services.auth)
    api(libs.play.services.location)
    api(libs.play.services.auth.api.phone)

    //glance
    api(libs.androidx.glance)
    api(libs.androidx.glance.appwidget)
    api(libs.androidx.glance.material3)

    //chucker
    debugApi(libs.chucker.debug)
    releaseApi(libs.chucker.release)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}