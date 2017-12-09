package pl.polsl.mateusz.chudy.mobileapplication.commands

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Reader

/**
 *
 */
data class AcknowledgeCommand(val result: Boolean) {

    class Deserializer : ResponseDeserializable<AcknowledgeCommand> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, AcknowledgeCommand::class.java)!!
    }
}