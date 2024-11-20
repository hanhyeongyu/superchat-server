package com.example.template.model

@JvmInline
value class Email(val value: String){

    init {
        if (!isValidEmail()){
            throw IllegalArgumentException("Email is not valid")
        }
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return value.matches(emailRegex)
    }
}