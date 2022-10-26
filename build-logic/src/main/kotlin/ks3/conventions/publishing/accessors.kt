package ks3.conventions.publishing

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugins.signing.SigningExtension

// Hacks, because IntelliJ is refusing to load the generated Gradle Kotlin DSL accessors.
// These aren't necessary for building, only so auto-complete works.

internal val Project.signing get() = extensions.getByType<SigningExtension>()
internal fun Project.signing(action: SigningExtension.() -> Unit) = extensions.configure(action)
internal val Project.publishing get() = extensions.getByType<PublishingExtension>()
internal fun Project.publishing(action: PublishingExtension.() -> Unit) = extensions.configure(action)
