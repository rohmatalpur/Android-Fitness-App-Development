plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Material Design 3 Components
    implementation("com.google.android.material:material:1.11.0")

    // SwipeRefreshLayout for pull-to-refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Shimmer for skeleton loading
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Navigation Components for better navigation
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")

    // Fragment for modern architecture
    implementation("androidx.fragment:fragment:1.6.2")

    // ViewPager2 for tabbed navigation
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("androidx.media3:media3-session:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Lottie for animations
    implementation ("com.airbnb.android:lottie:6.1.0")

    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // Blur effects
    implementation ("jp.wasabeef:blurry:4.0.1")

    // Circular ProgressBar
    implementation ("com.mikhaellopez:circularprogressbar:3.1.0")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-rxjava2:2.6.1")

    // WorkManager for background tasks
    implementation("androidx.work:work-runtime:2.9.0")

    // Lifecycle components for better architecture
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")

    // Firebase BoM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // MPAndroidChart for statistics graphs
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // CardView for modern UI
    implementation("androidx.cardview:cardview:1.0.0")

    // CircleImageView for profile pictures
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // YouTube Player library - handles YouTube's restrictions better
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
}