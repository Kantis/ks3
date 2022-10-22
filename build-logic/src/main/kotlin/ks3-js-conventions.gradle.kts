plugins {
   id("ks3-kotlin-conventions")
}

kotlin {
   targets {
      js(BOTH) {
         browser()
         nodejs()
      }
   }
}
