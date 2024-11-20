package com.example.template.file

import com.example.template.connector.FileConnector
import com.example.template.file.PdfsController.Companion.ENDPOINT
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Paths

@RestController
@RequestMapping(ENDPOINT)
class PdfsController(
    private val fileConnector: FileConnector
){

    @GetMapping("/{dir}/{fileName}")
    fun display(
        @PathVariable dir: String,
        @PathVariable fileName: String
    ): ResponseEntity<Resource> {
        val path = Paths.get("pdfs/${dir}", fileName)
        val resource = fileConnector.resource(path)
        val contentDisposition = "inline"
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
            .contentType(MediaType.APPLICATION_PDF)
            .body(resource)
    }

    companion object{
        internal const val ENDPOINT = "pdfs"
    }

}