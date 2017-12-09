package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.polsl.mateusz.chudy.mobileapplication.commands.AcknowledgeCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.Room

/**
 *
 */
object RoomService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getRooms(): List<Room> =
        "/api/room".httpGet()
                .rx_object(Room.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getRoom(roomId: Long): Room =
        "/api/room/$roomId".httpGet()
                .rx_object(Room.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createRoom(room: Room): Room =
        "/api/room".httpPost()
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(room))
                .rx_object(Room.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateRoom(room: Room): Room =
        "/api/room".httpPut()
                .body(Gson().toJson(room))
                .rx_object(Room.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteRoom(roomId: Long): Boolean =
        "/api/room/$roomId".httpDelete()
                .rx_object(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get().result }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getRoomModuleConfigurations(roomId: Long): List<ModuleConfiguration> =
            "/api/room/module-configuration/$roomId".httpGet()
                    .rx_object(ModuleConfiguration.ListDeserializer())
                    .subscribeOn(Schedulers.newThread())
                    .map { it -> it.get() }
                    .onErrorReturn { throw it }
                    .blockingGet()
}
