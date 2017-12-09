package pl.polsl.mateusz.chudy.mobileapplication.commands

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class SwitchCommand(
        val moduleId: Long,
        val switchNo: Short,
        val state: Boolean
) {
    class Deserializer : ResponseDeserializable<SwitchCommand> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, SwitchCommand::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<SwitchCommand>> {

        override fun deserialize(reader: Reader): List<SwitchCommand> {
            val type = object : TypeToken<List<SwitchCommand>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}
