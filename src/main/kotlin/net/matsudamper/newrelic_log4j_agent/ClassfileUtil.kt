package net.matsudamper.newrelic_log4j_agent

import net.matsudamper.newrelic_log4j_agent.classloader.JVMAgentClassLoader

class ClassfileUtil(
    private val loader: ClassLoader
) {
    fun getByteArray(classFullName: String): ByteArray {
        val internalPath = classFullName.replace('.', '/') + ".class"

        val res = loader.getResource(internalPath)!!

        return res.openStream().use {
            it.readBytes()
        }
    }

    companion object {
        fun getAgentClassLoader(): ClassfileUtil {
            val agentJarUrl = Premain::class.java.protectionDomain.codeSource.location
            val loader = JVMAgentClassLoader(
                arrayOf(agentJarUrl),
                null
            )
            return ClassfileUtil(loader)
        }

        fun getClassLoader() : ClassfileUtil {
            return ClassfileUtil(Premain::class.java.classLoader)
        }
    }
}