package com.example.template.application

import java.nio.file.Path

interface VideoProcessor {
    fun process(videoPath: Path, outputPath: Path)
}