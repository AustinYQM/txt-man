
plugins {
    pmd
    jacoco
    java
    application

    alias(libs.plugins.org.jetbrains.kotlin.jvm)

    alias(libs.plugins.com.diffplug.spotless)
    alias(libs.plugins.com.github.spotbugs)
    alias(libs.plugins.com.github.ben.manes.versions)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
    alias(libs.plugins.com.adarshr.test.logger)
    alias(libs.plugins.io.freefair.lombok)
    alias(libs.plugins.com.gradleup.shadow)
}

group = "com.yqmonline"
version = "1.0-SNAPSHOT"
val snippetsDir by extra { file("build/generated-snippets") }

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation(libs.org.yaml.snakeyaml) {
            because("Shit is super borked")
        }
        implementation(libs.com.github.ben.manes.caffeine.caffeine)
        implementation(libs.ch.qos.logback.logback.classic)
        implementation(libs.ch.qos.logback.logback.core)
    }
    implementation(libs.org.hexworks.zircon.zircon.core.jvm)
    implementation(libs.org.hexworks.zircon.zircon.jvm.swing)
    implementation(libs.org.slf4j.slf4j.api)
//    implementation(libs.org.slf4j.slf4j.simple)

    testImplementation(kotlin("test"))
    testImplementation(libs.org.junit.jupiter.junit.jupiter.api)
    testImplementation(libs.org.mockito.mockito.core)
    testImplementation(libs.org.assertj.assertj.core)
}

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.dir(snippetsDir)

    finalizedBy(tasks.jacocoTestReport)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        ktlint()
    }
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    ignoreFailures = true
    showProgress = true
    effort = com.github.spotbugs.snom.Effort.MAX
    reportLevel = com.github.spotbugs.snom.Confidence.LOW
}
tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
    reports.create("html").required = true
    reports.create("xml").required = true
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// Tells dependency plugin to reject upgrade options if they aren't full releases.
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) and !isNonStable(currentVersion)
    }
}

tasks.jacocoTestReport {
    reports.xml.required = true
    reports.html.required = true

    dependsOn(tasks.test)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.85".toBigDecimal()
                isFailOnViolation = false
            }
        }
    }
}

configure<com.adarshr.gradle.testlogger.TestLoggerExtension> {
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA_PARALLEL
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(tasks.spotlessApply)
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("com.yqmonline.txt-man")
    mergeServiceFiles()
}
tasks.build { dependsOn(tasks.shadowJar) }

val jar by tasks.getting(Jar::class) {
    manifest.attributes["Main-Class"] = "com.yqmonline.MainKt"
}

application {
    mainClass = "com.yqmonline.MainKt"
}
