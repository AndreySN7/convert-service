plugins {
	java
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
}
val springAiVersion by extra("2.0.1")

group = "com.events"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-kafka")
	implementation("io.minio:minio:9.0.3")
	implementation("org.apache.pdfbox:pdfbox:3.0.8")
	implementation("com.itextpdf:itextpdf:5.5.13.6")
//	implementation("org.springframework.boot:spring-boot-starter-liquibase")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
//	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
	testImplementation("org.springframework.boot:spring-boot-starter-kafka-test")
//	testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:$springAiVersion")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
