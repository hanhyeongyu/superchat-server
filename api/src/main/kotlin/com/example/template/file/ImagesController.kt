package com.example.template.file

import com.example.template.connector.FileConnector
import com.example.template.file.ImagesController.Companion.ENDPOINT
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files
import java.nio.file.Paths


@RestController
@RequestMapping(ENDPOINT)
class ImagesController(
    private val fileConnector: FileConnector
){

    @GetMapping("/{dir}/{fileName}")
    fun display(
        @PathVariable dir: String,
        @PathVariable fileName: String
    ): ResponseEntity<Resource>{
        val path = Paths.get("images/$dir", fileName)
        val resource =  fileConnector.resource(path)
        val contentDisposition = "inline"
        val contentType = Files.probeContentType(resource.file.toPath())

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource)
    }


    companion object{
        internal const val ENDPOINT = "images"
    }

}