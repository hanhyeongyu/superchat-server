package com.example.template.connector

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

interface FileConnector {
    fun createDirectories(dir: String)

    fun save(file: MultipartFile, path: Path)
    fun delete(path: Path): Boolean

    fun url(path: Path): String
    fun resource(path: Path): Resource
}