package ks3.conventions

import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject

/**
 * Common settings for configuring ks3's build logic.
 *
 * The settings need to be accessible during configuration, so they should come from Gradle
 * properties or environment variables.
 */
abstract class Ks3BuildLogicSettings @Inject constructor(
   private val providers: ProviderFactory,
) {

   val kotlinTarget: Provider<String> = ks3Setting("kotlinTarget", "1.6")
   val jvmTarget: Provider<String> = ks3Setting("jvmTarget", "11")

   /** Controls whether Kotlin Multiplatform JVM is enabled */
   val enableKotlinMultiplatformJvm: Provider<Boolean> = ks3Flag("enableKotlinMultiplatformJvm", true)
   /** Controls whether Kotlin Multiplatform JS is enabled */
   val enableKotlinMultiplatformJs: Provider<Boolean> = ks3Flag("enableKotlinMultiplatformJs", true)
   /** Controls whether Kotlin Multiplatform Native is enabled */
   val enableKotlinMultiplatformNative: Provider<Boolean> = ks3Flag("enableKotlinMultiplatformNative", false)

   private fun ks3Setting(name: String, default: String? = null) =
      providers.gradleProperty("ks3_$name")
         .orElse(providers.provider { default }) // workaround for https://github.com/gradle/gradle/issues/12388

   private fun ks3Flag(name: String, default: Boolean) =
      providers.gradleProperty("ks3_$name").map { it.toBoolean() }.orElse(default)
}
