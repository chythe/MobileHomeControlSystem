package pl.polsl.mateusz.chudy.mobileapplication.api

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.rx.rx_responseObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import pl.polsl.mateusz.chudy.mobileapplication.commands.AcknowledgeCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.Room

/**
 *
 */
object RoomApi {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getRooms(): List<Room> =
        "/api/room".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(Room.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getRoom(roomId: Long): Room =
        "/api/room/$roomId".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(Room.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createRoom(room: Room): Room =
        "/api/room".httpPost()
                .header("Authorization" to AuthenticationApi.getToken())
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(room))
                .rx_responseObject(Room.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateRoom(room: Room): Room =
        "/api/room".httpPut()
                .header("Authorization" to AuthenticationApi.getToken())
                .body(Gson().toJson(room))
                .rx_responseObject(Room.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteRoom(roomId: Long): Boolean =
        "/api/room/$roomId".httpDelete()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!.result
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getRoomModuleConfigurations(roomId: Long): List<ModuleConfiguration> =
        "/api/room/module-configuration/$roomId".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(ModuleConfiguration.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()
}
