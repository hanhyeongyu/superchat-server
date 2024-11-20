package com.example.template.projection

import com.example.template.model.User

interface UserReadModel {
    fun user(email: String): User?
}