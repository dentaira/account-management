plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.flywaydb.flyway") version "9.22.3"
    id("org.jooq.jooq-codegen-gradle") version "3.19.6"
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

    jooqCodegen("org.postgresql:postgresql")

//    implementation("org.jooq:jooq-postgres-extensions")

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
            srcDir("src/main/generated")
        }
    }
}

jooq {
    // This is the configuration for the code generation task
    // See https://www.jooq.org/doc/3.15/manual/code-generation/codegen-gradle/
    // for more details
    configuration {
        logging = org.jooq.meta.jaxb.Logging.DEBUG // TODO
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/mydatabase"
            user = "myuser"
            password = "secret"
        }
        generator {
            database {
                includes = "public.*"
                excludes = "flyway_schema_history"
                forcedTypes {
                    forcedType {
                        name = "INSTANT"
                        includeTypes = "timestamptz"
                    }
                }
            }
            target {
                packageName = "dentaira.accountmanagement.generated"
                directory = "src/main/generated"
            }
        }
    }
}