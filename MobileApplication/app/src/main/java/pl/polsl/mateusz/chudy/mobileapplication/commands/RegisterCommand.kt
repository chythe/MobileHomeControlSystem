package pl.polsl.mateusz.chudy.mobileapplication.commands

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class RegisterCommand(
        @SerializedName("username") val username: String,
        @SerializedName("password") val password: String
)