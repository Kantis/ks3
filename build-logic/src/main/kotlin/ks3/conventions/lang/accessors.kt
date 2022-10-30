package ks3.conventions.lang

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


// hacks because IntelliJ is refusing to index generated accessors

internal val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByType()
internal fun Project.kotlin(configure: KotlinMultiplatformExtension.() -> Unit) = extensions.configure(configure)
