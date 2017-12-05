package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class Module(
        val moduleId: Long = 0,
        @Expose val name: String = ""
): java.io.Serializable {

    class Deserializer : ResponseDeserializable<Module> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, Module::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<Module>> {

        override fun deserialize(reader: Reader): List<Module> {
            val type = object : TypeToken<List<Module>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}
