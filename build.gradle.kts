import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
	}
}

plugins {
	idea
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	id("com.google.protobuf") version "0.8.18"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val grpcVersion = "1.42.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


	// GRPC

	// https://mvnrepository.com/artifact/io.github.lognet/grpc-spring-boot-starter
	implementation("io.github.lognet:grpc-spring-boot-starter:4.5.10")
	// https://mvnrepository.com/artifact/io.grpc/grpc-kotlin-stub
	api("io.grpc:grpc-kotlin-stub:1.2.0")
	// https://mvnrepository.com/artifact/io.grpc/protoc-gen-grpc-kotlin
	implementation("io.grpc:protoc-gen-grpc-kotlin:1.2.0")
	// https://mvnrepository.com/artifact/io.grpc/grpc-protobuf
	implementation("io.grpc:grpc-protobuf:${grpcVersion}")
	// https://mvnrepository.com/artifact/io.grpc/grpc-stub
	implementation("io.grpc:grpc-stub:${grpcVersion}")
	// https://mvnrepository.com/artifact/io.grpc/grpc-netty
	implementation("io.grpc:grpc-netty:${grpcVersion}")
	// https://mvnrepository.com/artifact/io.grpc/grpc-all
	implementation("io.grpc:grpc-all:${grpcVersion}")
	// https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
	implementation("com.google.protobuf:protobuf-java:3.19.1")
	// https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
	// https://mvnrepository.com/artifact/com.google.protobuf/protobuf-gradle-plugin
	runtimeOnly("com.google.protobuf:protobuf-gradle-plugin:0.8.18")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	main {
		proto {
			srcDir("src/main/resources/proto")
		}
	}
}

protobuf {
	protoc{
		artifact = "com.google.protobuf:protoc:3.19.1"
	}
	plugins {
		id("grpc"){
			artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.0:jdk7@jar"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}

idea {
	module {
		sourceDirs.add(file("${projectDir}/build/generated/source/proto/main/java"))
		sourceDirs.add(file("${projectDir}/build/generated/source/proto/main/grpckt"))
		sourceDirs.add(file("${projectDir}/build/generated/source/proto/main/grpc"))
	}
}
