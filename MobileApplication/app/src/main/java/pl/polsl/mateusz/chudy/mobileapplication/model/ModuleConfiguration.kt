package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class ModuleConfiguration(
        @Expose var moduleId: Long = 0,
        @Expose var switchNo: Short = 0,
        @Expose var roomId: Long = 0,
        @Expose var switchTypeId: Long = 0,
        @Expose var name: String = "",
        var state: Boolean = false
): java.io.Serializable {

    override fun toString(): String {
        return name
    }

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