package pl.polsl.mateusz.chudy.mobileapplication.commands

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class RegisterCommand(
        val username: String,
        val password: String
)