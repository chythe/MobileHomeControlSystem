package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class ModuleConfiguration(
        val moduleId: Long = 0,
        val switchNo: Short = 0,
        val roomId: Long = 0,
        val switchTypeId: Long = 0,
        val name: String = ""
): java.io.Serializable {

    class Deserializer : ResponseDeserializable<ModuleConfiguration> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, ModuleConfiguration::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<ModuleConfiguration>> {

        override fun deserialize(reader: Reader): List<ModuleConfiguration> {
            val type = object : TypeToken<List<ModuleConfiguration>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}