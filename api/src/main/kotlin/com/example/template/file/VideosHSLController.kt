package com.example.template.file


import com.example.template.connector.FileConnector
import com.example.template.file.VideosHSLController.Companion.ENDPOINT
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping(ENDPOINT)
class VideosHSLController(
    private val fileConnector: FileConnector
){

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{dir}/{videoId}/master.m3u8")
    fun serverMasterFile(
        @PathVariable dir: String,
        @PathVariable videoId: String
    ): ResponseEntity<Resource> {
        val path = Paths.get("video_hsl", dir, videoId,"master.m3u8")
        log.debug(path.toString())

        if (!Files.exists(path)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val resource: Resource = fileConnector.resource(path)

        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_TYPE, "application/vnd.apple.mpegurl"
            )
            .body(resource)
    }


    //serve the segments
    @GetMapping("/{dir}/{videoId}/{segment}.ts")
    fun serveSegments(
        @PathVariable dir: String,
        @PathVariable videoId: String,
        @PathVariable segment: String
    ): ResponseEntity<Resource> {
        val path = Paths.get("video_hsl", dir, videoId, "$segment.ts")
        log.debug(path.toString())

        if (!Files.exists(path)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val resource: Resource = fileConnector.resource(path)
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_TYPE, "video/mp2t"
            )
            .body(resource)
    }

    companion object{
        internal const val ENDPOINT = "video_hsl"
    }
}