package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import java.io.Reader

/**
 *
 */
data class User(
        val userId: Long = 0,
        @Expose val username: String = "",
        @Expose val password: String = "",
        @Expose val role: Role = Role.GUEST
): java.io.Serializable {

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