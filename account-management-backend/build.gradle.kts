import org.jooq.meta.jaxb.ForcedType

plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.flywaydb.flyway") version "9.22.3"
    id("nu.studer.jooq") version "9.0"
//    id("org.jooq.jooq-codegen-gradle") version "???" // Spring Bootのバージョンを3.3.0に上げたらこちらに切り替える
    id("org.openapi.generator") version "7.4.0"
}

group = "dentaira"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springModulithVersion"] = "1.1.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.modulith:spring-modulith-starter-core")

    implementation("com.fasterxml.uuid:java-uuid-generator:5.0.0")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    // Modulith と actuator を同時に入れるとこれがないと動かない 2024/3/6
    // https://stackoverflow.com/questions/78013972/classnotfoundexception-io-micrometer-tracing-tracer-for-new-spring-boot-project
    implementation("io.micrometer:micrometer-tracing-bridge-otel")

//    implementation("org.jooq:jooq-postgres-extensions")

    jooqGenerator("org.postgresql:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

flyway {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/mydatabase"
    user = "myuser"
    password = "secret"
}

buildscript {
    repositories {
        mavenCentral()
    }
}

sourceSets {
    main {
        java {
            srcDir("src/main/generated") // fixme Intellij IDEA で src/main/generated が認識されない
        }
    }
}

jooq {
    version.set(dependencyManagement.importedProperties["jooq.version"])
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/mydatabase"
                    user = "myuser"
                    password = "secret"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = "flyway_schema_history"
                        forcedTypes.addAll(listOf(
                                ForcedType().apply {
                                    name = "INSTANT"
                                    includeTypes = "timestamptz"
                                },
                        ))
                    }
                    target.apply {
                        packageName = "dentaira.accountmanagement.generated"
                        directory = "src/main/generated/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}