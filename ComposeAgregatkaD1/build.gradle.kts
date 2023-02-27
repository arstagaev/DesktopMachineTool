import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.compose") version "1.3.0"
    kotlin("plugin.serialization") version "1.8.0"
    //id("kotlinx-serialization")
}

group = "me.tagaev"
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

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("com.fazecast:jSerialComm:2.9.3")

    implementation("org.apache.poi:poi:5.0.0")

    implementation("org.jfree:jcommon:1.0.24")
    implementation("org.jfree:jfreechart:1.5.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")


}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "15"
}

compose.desktop {
    val version = "1.2.1"
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Agregatka MachineTool"
            packageVersion = version

        }
        buildTypes.release {
            proguard {
                //isEnabled.set(false)
                configurationFiles.from("compose-desktop.pro")
            }
        }
    }

//    nativeDistributions {
//        val iconsRoot = project.file("src/main/resources")
//        linux {
//            iconFile.set(iconsRoot.resolve("drawables/linux_logo.png"))
//        }
//        windows {
//            iconFile.set(iconsRoot.resolve("drawables/windows_logo.png"))
//        }
//        macOS{
//            iconFile.set(iconsRoot.resolve("drawables/macOS_logo.png"))
//        }
//    }
}