import org.gradle.api.Project

/**
 * <p><b>Package:</b>  </p>
 * <p><b>Project:</b> My Application </p>
 * <p><b>Classname:</b> KDeps </p>
 * Created by leobert on 2021/6/8.
 */
object KDeps {

    class Kotlin {
        val stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10"
    }

    val kotlin = Kotlin()

    class Config {
        val minSdkVersion = 19
        val targetSdkVersion = 31
        val compileSdkVersion = 31
        val androidTools = "30.0.2"
    }

    val config = Config()

    class AndroidX {

        object Test {
            const val apply_policy = "androidTestImplementation"
            const val runner = "androidx.test:runner:1.4.0"
            const val ext_junit = "androidx.test.ext:junit:1.1.2"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

            fun applyAll(project: Project) {
                project.dependencies.add(apply_policy, runner)
                project.dependencies.add(apply_policy, ext_junit)
                project.dependencies.add(apply_policy, espresso)
            }
        }


        val CardView = "androidx.cardview:cardview:1.0.0"
        val RecyclerView = "androidx.recyclerview:recyclerview:1.2.0"
        val Appcompat = "androidx.appcompat:appcompat:1.4.0"
        val Annotation = "androidx.annotation:annotation:1.3.0"

        fun applyTest(project: Project) {
            Test.applyAll(project)
        }

    }

    class Test {
        companion object {
            val junit = "junit:junit:4.13.2"
        }

    }

    val androidX: AndroidX = AndroidX()

    fun applyTest(project: Project) {
        project.dependencies.add("testImplementation", Test.junit)
        androidX.applyTest(project)
    }
}