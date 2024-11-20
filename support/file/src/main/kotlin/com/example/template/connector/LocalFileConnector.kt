package com.example.template.connector

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path


@Service
class LocalFileConnector(
    @Value("\${file.baseUrl}")
    private val baseURL: String,
): FileConnector {

    override fun createDirectories(dir: String) {
        val path = Path(dir)
        Files.createDirectories(path)
    }

    override fun save(file: MultipartFile, path: Path) {
        file.transferTo(File(path.toUri()))
    }

    override fun delete(path: Path): Boolean {
        val file = File(path.toString())
        return if (file.exists()) {
            file.delete()

        } else {
            false
        }
    }

    override fun url(path: Path): String {
        var result = ""
        result += baseURL
        result += path.toString()
        return result
    }


    override fun resource(path: Path): Resource {
        //return UrlResource("file:${path}")
        return FileSystemResource(path)
    }
}