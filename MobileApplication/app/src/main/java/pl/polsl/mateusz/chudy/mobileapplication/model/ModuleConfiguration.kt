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
        @SerializedName("module_id") val moduleId: Long,
        @SerializedName("switch_no") val switchNo: Short,
        @SerializedName("room_id") val roomId: Long,
        @SerializedName("switch_type_id") val switchTypeId: Long,
        @SerializedName("name") val name: String
) {

    class Deserializer : ResponseDeserializable<ModuleConfiguration> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, ModuleConfiguration::class.java)
    }

    class ListDeserializer : ResponseDeserializable<List<ModuleConfiguration>> {

        override fun deserialize(reader: Reader): List<ModuleConfiguration> {
            val type = object : TypeToken<List<ModuleConfiguration>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}