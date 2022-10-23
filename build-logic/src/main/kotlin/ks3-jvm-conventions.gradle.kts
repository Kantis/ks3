plugins {
   id("ks3-kotlin-conventions")
   id("ks3-base-conventions")
}

kotlin {
   targets {
      jvm {
         withJava()

         compilations.all {
            kotlinOptions {
               jvmTarget = "1.8"
            }
         }
      }
   }
}

java {
   sourceCompatibility = JavaVersion.VERSION_1_8
   targetCompatibility = JavaVersion.VERSION_1_8
}
