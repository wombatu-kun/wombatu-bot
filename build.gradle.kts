import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE" apply false
	id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
	kotlin("jvm") version "1.3.71" apply false
	kotlin("plugin.spring") version "1.3.71" apply false
	kotlin("kapt") version "1.3.71" apply false
	kotlin("plugin.jpa") version "1.3.71" apply false
	kotlin("plugin.allopen") version "1.3.71" apply false
}

allprojects {
	group = "wombatukun.bots"
	version = "0.0.1-SNAPSHOT"

	tasks.withType<JavaCompile> {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	repositories {
		mavenCentral()
		jcenter()
	}

	apply {
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
	}
}
