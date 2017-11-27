package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig
import pl.polsl.mateusz.chudy.mobileapplication.model.Room


/**
 *
 */
class RoomService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getRooms() {
        "/api/room".httpGet()
                .responseObject(Room.ListDeserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val rs: List<Room> = result.get()
                            print("MC: Success " + rs)
                        }
                    }
        }
    }

    fun getRoom(roomId: Long) {
        "/api/room/$roomId".httpGet()
                .responseObject(Room.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val r: Room = result.get()
                            print("MC: Success " + r)
                        }
                    }
                }
    }

    fun createRoom(room: Room) {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        "/api/room".httpPost()
                .body(gson.toJson(room))
                .responseObject(Room.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val r: Room = result.get()
                            print("MC: Success " + r)
                        }
                    }
                }
    }

    fun updateRoom(room: Room) {
        val gson = Gson()
        "/api/room".httpPut()
                .body(gson.toJson(room))
                .responseObject(Room.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val r: Room = result.get()
                            print("MC: Success " + r)
                        }
                    }
                }
    }

    fun deleteRoom(roomId: Long) {
        "/api/room/$roomId".httpDelete()
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val result: String = result.get()
                            print("MC: Success " + result)
                        }
                    }
                }
    }
}
