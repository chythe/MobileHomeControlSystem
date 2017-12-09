package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import java.io.Reader

/**
 *
 */
data class User(
        var userId: Long = 0,
        @Expose var username: String = "",
        @Expose var password: String = "",
        @Expose var role: Role = Role.GUEST
): java.io.Serializable {

    override fun toString(): String {
        return username
    }

    class Deserializer : ResponseDeserializable<User> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, User::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<User>> {

        override fun deserialize(reader: Reader): List<User> {
            val type = object : TypeToken<List<User>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}