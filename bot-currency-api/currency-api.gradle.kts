import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("kapt")
}

dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-hystrix:2.1.2.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	kapt("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.apache.httpcomponents:httpclient:4.5.9")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true