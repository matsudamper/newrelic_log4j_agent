package net.matsudamper.newrelic_log4j_agent

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import kotlin.jvm.Throws

object JarUtil {

    @Throws(IOException::class)
    fun createJarFile(
        prefix: String,
        tmpDir: File,
        classes: Map<String, ByteArray>,
        manifest: Manifest = Manifest()
    ): File {
        val file = File.createTempFile(prefix, ".jar", tmpDir)

        val outStream = JarOutputStream(FileOutputStream(file), manifest)
        writeFilesToJarStream(classes, outStream)
        return file
    }

    @Throws(IOException::class)
    private fun writeFilesToJarStream(
        classes: Map<String, ByteArray>,
        outStream: JarOutputStream
    ) {
        val resources: MutableMap<String, ByteArray> = HashMap()
        for ((key, value) in classes) {
            resources[key.replace('.', '/') + ".class"] = value
        }
        try {
            addJarEntries(outStream, resources)
        } finally {
            try {
                outStream.close()
            } catch (ioe: IOException) {
            }
        }
    }

    @Throws(IOException::class)
    private fun addJarEntries(
        jarStream: JarOutputStream,
        files: Map<String, ByteArray>
    ) {
        for ((key, value) in files) {
            val jarEntry = JarEntry(key)
            jarStream.putNextEntry(jarEntry)
            jarStream.write(value)
            jarStream.closeEntry()
        }
    }
}