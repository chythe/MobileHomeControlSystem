package pl.polsl.mateusz.chudy.mobileapplication.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 *
 */
@Entity
data class Module(
        @PrimaryKey var moduleId: Long = 0,
        @Expose var name: String = "",
        @Expose var ipAddress: String = ""
): java.io.Serializable {

    override fun toString(): String {
        return name
    }

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
