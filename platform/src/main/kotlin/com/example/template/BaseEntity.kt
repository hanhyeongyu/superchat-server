package com.example.template

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@MappedSuperclass
abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    open val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @Column
    open var updatedAt: LocalDateTime? = null
        protected set
}