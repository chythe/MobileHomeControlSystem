package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class Room(
        @SerializedName("room_id") val roomId: Long,
        @SerializedName("name") val name: String
) {

    class Deserializer : ResponseDeserializable<Room> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, Room::class.java)
    }

    class ListDeserializer : ResponseDeserializable<List<Room>> {

        override fun deserialize(reader: Reader): List<Room> {
            val type = object : TypeToken<List<Room>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}
