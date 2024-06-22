package com.coderunners.heytripsv.model

data class CreateUserData (
    val name: String= "",
    val email: String= "",
    val password: String="",
    val confirmPassword: String="",
)

