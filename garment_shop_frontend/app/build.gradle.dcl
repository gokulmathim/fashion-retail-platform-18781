androidApplication {
    namespace = "org.example.app"

    // Note: Declarative DSL handles defaultConfig via defaults in settings.gradle.dcl.
    // We avoid android{} and buildFeatures{} blocks here to keep compatibility.

    dependencies {
        // AndroidX core + appcompat + material
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.activity:activity-ktx:1.9.2")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

        // JSON parsing (ready for future backend integration)
        implementation("com.squareup.moshi:moshi:1.15.1")
        implementation("com.squareup.moshi:moshi-kotlin:1.15.1")

        // Keep sample modules available
        implementation(project(":utilities"))
        implementation(project(":list"))
        implementation("org.apache.commons:commons-text:1.11.0")
    }
}
