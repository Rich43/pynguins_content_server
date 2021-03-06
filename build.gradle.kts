import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.System.getenv

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	`maven-publish`
}

buildscript {
	tasks.findByName("build")?.dependsOn("bootJar")
}

group = "com.archesky"
version = "0.0.${getenv().getOrDefault("GITHUB_RUN_ID", "1")}-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	jcenter()
	maven("https://maven.pkg.github.com/Rich43/archesky-auth-library") {
		credentials {
			username = "Rich43"
			password = getenv()["GITHUB_TOKEN"]
		}
	}
	maven("https://maven.pkg.github.com/Rich43/archesky-ssl-library") {
		credentials {
			username = "Rich43"
			password = getenv()["GITHUB_TOKEN"]
		}
	}
	maven("https://maven.pkg.github.com/Rich43/archesky-apollo-library") {
		credentials {
        		username = "Rich43"
        		password = getenv()["GITHUB_TOKEN"]
        	}
	}
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
	implementation("io.reactivex.rxjava2:rxjava:2.2.19")
	implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:7.1.0")
	implementation("com.graphql-java-kickstart:graphql-kickstart-spring-webflux:7.1.0")
	implementation("com.graphql-java-kickstart:graphql-kickstart-spring-boot-starter-tools:7.1.0")
	implementation("com.arangodb:arangodb-java-driver:6.6.0")
	implementation("com.arangodb:arangodb-spring-data:3.2.3")
	implementation("com.archesky.auth.library:archesky-auth-library:0.0.281083311-SNAPSHOT")
	implementation("com.archesky.ssl.library:archesky-ssl-library:0.0.283041548-SNAPSHOT")
	runtimeOnly("com.graphql-java-kickstart:altair-spring-boot-starter:7.1.0")
	runtimeOnly("com.graphql-java-kickstart:graphiql-spring-boot-starter:7.1.0")
	runtimeOnly("com.graphql-java-kickstart:voyager-spring-boot-starter:7.1.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("com.graphql-java-kickstart:graphql-spring-boot-test:7.1.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/Rich43/archesky-content-server")
			credentials {
				username = getenv().getOrDefault("GITHUB_ACTOR", "Rich43")
				password = getenv()["GITHUB_TOKEN"]
			}
		}
	}
	publications {
		create<MavenPublication>("gpr") {
			from(components["java"])
			artifact(tasks.getByName("bootJar"))
		}
	}
}
