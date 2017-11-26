package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class SwitchType(
        @SerializedName("switch_type_id") val switchTypeId: Long,
        @SerializedName("name") val name: String
) {

    class Deserializer : ResponseDeserializable<SwitchType> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, SwitchType::class.java)
    }

    class ListDeserializer : ResponseDeserializable<List<SwitchType>> {

        override fun deserialize(reader: Reader): List<SwitchType> {
            val type = object : TypeToken<List<SwitchType>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}
