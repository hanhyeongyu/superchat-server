package com.example.template.repository

import com.example.template.model.Video
import org.springframework.data.jpa.repository.JpaRepository


interface VideoRepository: JpaRepository<Video, Long>