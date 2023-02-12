import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.compose") version "1.3.0"
}

group = "me.agaev"
version = "1.0"

repositories {
    google()
    mavenCentral()
    jcenter()
    maven ("https://jitpack.io" )
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven ("https://repo1.maven.org/maven2/")
}

dependencies {
    implementation(compose.desktop.windows_x64)
    //implementation(// https://mvnrepository.com/artifact/com.fazecast/jSerialComm
    //implementation("com.fazecast:jSerialComm:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
//    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    implementation("com.fazecast:jSerialComm:2.9.3")

    // https://mvnrepository.com/artifact/org.apache.poi/poi
    implementation("org.apache.poi:poi:5.0.0")
    //implementation("com.github.tehras:charts:beta-01")
    //implementation("io.github.bytebeats:compose-charts:0.1.0")
    // https://mvnrepository.com/artifact/jfree/jfreechart

    implementation("org.jfree:jcommon:1.0.24")
    implementation("org.jfree:jfreechart:1.5.3")

}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ComposeAgregatkaD1"
            packageVersion = "1.1.4"
        }
    }
}