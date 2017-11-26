package pl.polsl.mateusz.chudy.mobileapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
data class User(
        @SerializedName("user_id") val userId: Long,
        @SerializedName("username") val username: String,
        @SerializedName("password") val password: String,
        @SerializedName("role") val role: String
) {

    class Deserializer : ResponseDeserializable<User> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, User::class.java)
    }

    class ListDeserializer : ResponseDeserializable<List<User>> {

        override fun deserialize(reader: Reader): List<User> {
            val type = object : TypeToken<List<User>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}