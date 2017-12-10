package pl.polsl.mateusz.chudy.mobileapplication.commands

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Reader

/**
 *
 */
data class LoginCommand(
        val username: String = "",
        val password: String = ""
) {
    class Deserializer : ResponseDeserializable<LoginCommand> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, LoginCommand::class.java)!!
    }
}