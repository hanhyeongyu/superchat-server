package com.example.template.application

import com.example.template.common.exception.IllegalStatusException
import com.example.template.common.exception.BaseException
import com.example.template.common.exception.ErrorCode.COMMON_SYSTEM_ERROR
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path


@Service
class LocalVideoProcessor: VideoProcessor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun process(videoPath: Path, outputPath: Path) {
        if (!Files.exists(videoPath)){
            throw IllegalStatusException("Video path $videoPath does not exist")
        }

        try{
            Files.createDirectories(outputPath)
            val ffmpegCmd = java.lang.String.format(
                "ffmpeg -i \"%s\" -c:v libx264 -c:a aac -strict -2 -f hls -hls_time 10 -hls_list_size 0 -hls_segment_filename \"%s/segment_%%3d.ts\"  \"%s/master.m3u8\" ",
                videoPath, outputPath, outputPath
            )
            log.debug(ffmpegCmd)

            val processBuilder = ProcessBuilder("/bin/bash", "-c", ffmpegCmd)
            processBuilder.inheritIO()
            val process = processBuilder.start()
            val exit = process.waitFor()
            if (exit != 0) {
                throw RuntimeException("video processing failed!!")
            }

        }catch (exception: IOException){
            throw BaseException("Video processing fail!", COMMON_SYSTEM_ERROR)
        }
    }
}