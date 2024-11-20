package com.example.template.service

import com.example.template.model.Video
import org.springframework.web.multipart.MultipartFile

interface VideoService {
    fun save(multipartFile: MultipartFile)

    fun video(id: Long): Video?

    fun process(id: Long)
}