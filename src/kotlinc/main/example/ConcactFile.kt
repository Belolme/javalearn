package kotlinc.main.example

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * use for concat so many file to a file
 * <p/>
 * Created by Billin on 2017/5/22.
 */
fun main(args: Array<String>) {
    copyDirSourceCodeToFile("D://target.txt") {
        dir("G:\\Java\\eclipse_workspace\\drawboard")

        dir("G:\\Java\\eclipse_workspace\\cotogo")

        dir("G:\\Android\\cotogo_android\\app")
    }
}

fun copyDirSourceCodeToFile(target: String, action: CopyDirSourceCodeToFile.() -> Unit) {
    val copyCodeToFile = CopyDirSourceCodeToFile(target)
    copyCodeToFile.action()
    copyCodeToFile.magic()
}

class CopyDirSourceCodeToFile(val target: String) {

    private val sourcePaths = mutableListOf<String>()

    fun dir(sourceDir: String) {
        sourcePaths.add(sourceDir)
    }

    fun magic() {
        val fileOutput = File(target)
        if (fileOutput.exists()) {
            fileOutput.delete()
        }
        fileOutput.createNewFile()

        sourcePaths.forEach {
            val fileInputDir = File(it)

            if (!fileInputDir.exists() || !fileInputDir.isDirectory) {
                println("$it is not directory, invalid input")
                return@forEach
            }

            writeDirToFile(fileInputDir, fileOutput)
        }
    }

    private fun writeDirToFile(sourceDir: File, target: File) {
        sourceDir.list()
                .filter {
                    if (it == null) {
                        false
                    } else {
                        !it.contains("build")
                                && !it.contains("target")
                                && !it.contains(".git")
                                && !it.contains(".settings")
                                && (it.contains(".java")
                                || it.contains(".xml")
                                || it.contains(".properties")
                                || it.contains(".gradle")
                                || it.contains(".pro")
                                || File(sourceDir, it).isDirectory)
                    }
                }
                .map { File(sourceDir, it) }
                .forEach { if (it.isDirectory) writeDirToFile(it, target) else writeToFile(it, target) }
    }

    private fun writeToFile(source: File, target: File) {
        val fileInputStream = FileInputStream(source)
        val fileOutputStream = FileOutputStream(target, true)

        val bufferReader = fileInputStream.bufferedReader()
        val bufferWriter = fileOutputStream.bufferedWriter()

        bufferReader.forEachLine { bufferWriter.write(it + "\n") }

        bufferWriter.close()
        bufferReader.close()
        fileOutputStream.close()
        fileInputStream.close()
    }
}