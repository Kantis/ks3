import ks3.conventions.relocateKotlinJsStore

plugins {
   id("ks3-kotlin-conventions")
   id("ks3-base-conventions")
}

kotlin {
   targets {
      js(BOTH) {
         browser()
         nodejs()
      }
   }
}

relocateKotlinJsStore()
