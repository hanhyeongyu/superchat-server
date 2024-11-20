package com.example.template.common.response

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter


@Configuration
class ResponseFormat {

    companion object{
        private const val DATE_FORMAT: String = "yyyy-MM-dd"
        private const val DATE_TIME_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss"
    }


    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            //DateTimeFormatter.ISO_LOCAL_DATE_TIME
            builder.serializers(
                LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT))
            )
            ////DateTimeFormatter.ISO_LOCAL_DATE_TIME
            builder.serializers(
                LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            )
        }
    }
}