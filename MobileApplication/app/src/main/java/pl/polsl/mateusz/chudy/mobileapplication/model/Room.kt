package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 * Model class of room
 */
data class Room(
        var roomId: Long = 0,
        @Expose var name: String = ""
): java.io.Serializable {

    override fun toString(): String {
        return name
    }

    class Deserializer : ResponseDeserializable<Room> {

        override fun deserialize(reader: Reader) =
                Gson().fromJson(reader, Room::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<Room>> {

        override fun deserialize(reader: Reader): List<Room> {
            val type = object : TypeToken<List<Room>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}
