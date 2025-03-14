plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.10"
}

group = "com.happy.block"
version = "0.0.1-SNAPSHOT"


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://hyperledger.jfrog.io/artifactory/besu-maven/") }
    maven { url = uri( "https://artifacts.consensys.net/public/maven/maven/") }
    maven { url = uri( "https://splunk.jfrog.io/splunk/ext-releases-local") }
    maven { url = uri( "https://dl.cloudsmith.io/public/consensys/quorum-mainnet-launcher/maven/") }

}

dependencies {

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.5.15")
    implementation("ch.qos.logback:logback-core:1.5.15")

    configurations.all {
        exclude(group = "org.slf4j", module = "slf4j-simple") // Prevents SLF4J conflicts
        exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j") // Prevents Log4j conflicts
    }

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    // Spring Boot JPA + Hibernate
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Web3j for Ethereum Blockchain Integration
    implementation("org.web3j:core:4.13.0")
    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")

    //=================TEST config =================/
    testImplementation(platform("org.junit:junit-bom:5.11.4"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")

    testImplementation("org.web3j:web3j-evm:4.13.0")

    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine") // ✅ Prevents JUnit 4 conflicts
    }

}

tasks.withType<Test> {
    useJUnitPlatform() // ✅ Forces Gradle to use JUnit 5
    testLogging.showStandardStreams = true
}