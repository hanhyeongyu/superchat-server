package com.example.template.model

import org.springframework.web.multipart.MultipartFile
import java.util.*


class FileUtils{
    companion object {
        fun createName(multipartFile: MultipartFile): String{
            val ext = extractExt(multipartFile.originalFilename!!)
            val uuid = UUID.randomUUID().toString()
            return "$uuid.$ext"
        }

        private fun extractExt(originalFileName: String): String{
            val pos = originalFileName.lastIndexOf(".")
            return originalFileName.substring(pos+1)
        }
    }
}