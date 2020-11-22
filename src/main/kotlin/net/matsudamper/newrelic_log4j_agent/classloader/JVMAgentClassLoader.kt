package net.matsudamper.newrelic_log4j_agent.classloader

import java.net.URL
import java.net.URLClassLoader
import java.text.MessageFormat

class JVMAgentClassLoader(
    urls: Array<URL>,
    parent: ClassLoader?
) : URLClassLoader(urls, parent) {
    companion object {
        init {
            try {
                ClassLoader.registerAsParallelCapable()
            } catch (t: Throwable) {
                System.err.println(
                    MessageFormat.format(
                        "Unable to register as parallel-capable: {0}",
                        t
                    )
                )
            }
        }
    }
}