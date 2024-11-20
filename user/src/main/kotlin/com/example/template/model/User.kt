package com.example.template.model

import com.example.template.BaseEntity
import jakarta.persistence.*

@Entity(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    var email: Email,

    @Column(length = 1000)
    var encodedPassword: EncodedPassword,
): BaseEntity()