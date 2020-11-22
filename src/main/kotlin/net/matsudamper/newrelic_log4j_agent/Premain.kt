package net.matsudamper.newrelic_log4j_agent

import com.google.common.reflect.ClassPath
import java.io.File
import java.lang.instrument.Instrumentation
import java.util.jar.JarFile

@Suppress("UnstableApiUsage")
class Premain {
    companion object {
        @JvmStatic
        fun premain(agentArgs: String?, instrumentation: Instrumentation) {
            val agentClassLoaderUtil = ClassfileUtil.getAgentClassLoader()
            val classLoaderUtil = ClassfileUtil.getClassLoader()

            val jar = JarUtil.createJarFile(
                prefix = "NewRelic_Class",
                tmpDir = tempDir,
                classes = mutableMapOf<String, ByteArray>().also { map ->
                    map.putAll(
                        listOf(
                            "com.newrelic.api.agent.NewRelic"
                        )
                            .map { it to agentClassLoaderUtil.getByteArray(it) }
                            .toMap()
                    )

                    map.putAll(
                        ClassPath.from(Premain::class.java.classLoader).allClasses
                            .filter { it.name.startsWith("com.newrelic.api.agent.") }
                            .filterNot { it.name == "com.newrelic.api.agent.NewRelic" }
                            .map { it.name }
                            .map { it to classLoaderUtil.getByteArray(it) }.toMap()
                    )

                    map.putAll(
                        ClassPath.from(Premain::class.java.classLoader).allClasses
                            .filter { it.name.startsWith("org.apache.logging.log4j.") }
                            .map { it.name }
                            .map { it to classLoaderUtil.getByteArray(it) }.toMap()
                    )
                }
            )

            instrumentation.appendToBootstrapClassLoaderSearch(JarFile(jar))
        }

        private val tempDir: File by lazy {
            val file = File("newrelic_tmp")
            if (file.exists()) {
                file.deleteRecursively()
            }
            file.mkdirs()
            file
        }
    }
}
