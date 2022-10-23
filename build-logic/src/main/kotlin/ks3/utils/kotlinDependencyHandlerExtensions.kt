@file:Suppress("PackageDirectoryMismatch")

package org.gradle.kotlin.dsl

import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderConvertible
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

// workarounds for https://youtrack.jetbrains.com/issue/KT-53396
// remove these when Kotlin 1.8 is released


/** @see org.gradle.api.artifacts.dsl.DependencyHandler.platform */
fun KotlinDependencyHandler.platform(
   notation: Any,
): Dependency =
   project.dependencies.platform(notation)

/** @see org.gradle.api.artifacts.dsl.DependencyHandler.platform */
fun KotlinDependencyHandler.platform(
   notation: Any,
   configureAction: Action<Dependency>,
): Dependency =
   project.dependencies.platform(notation, configureAction)

/** @see org.gradle.api.artifacts.dsl.DependencyHandler.platform */
fun KotlinDependencyHandler.platform(
   dependencyProvider: Provider<MinimalExternalModuleDependency>,
): Provider<MinimalExternalModuleDependency> =
   project.dependencies.platform(dependencyProvider)

/** @see org.gradle.api.artifacts.dsl.DependencyHandler.platform */
fun KotlinDependencyHandler.platform(
   dependencyProviderConvertible: ProviderConvertible<MinimalExternalModuleDependency>,
): Provider<MinimalExternalModuleDependency> =
   project.dependencies.platform(dependencyProviderConvertible)
