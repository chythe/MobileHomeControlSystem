package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig
import pl.polsl.mateusz.chudy.mobileapplication.model.Room


/**
 *
 */
class RoomService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
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
                            val rooms: List<Room> = result.get()
                            print("MC: Success " + rooms)
                        }
                    }
        }
    }

    fun getRoom() {
        TODO()
    }

    fun createRoom() {
        TODO()
    }

    fun updateRoom() {
        TODO()
    }

    fun deleteRoom() {
        TODO()
    }
}
