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
import io.reactivex.schedulers.Schedulers
import pl.polsl.mateusz.chudy.mobileapplication.commands.AcknowledgeCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import java.net.SocketTimeoutException

/**
 *
 */
object UserService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getUsers(): List<User> =
        "/api/user".httpGet()
                .rx_object(User.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getUser(userId: Long): User =
        "/api/user/$userId".httpGet()
                .rx_object(User.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createUser(user: User): User =
        "/api/user".httpPost()
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(user))
                .rx_object(User.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateUser(user: User): User =
        "/api/user".httpPut()
                .body(Gson().toJson(user))
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(user))
                .rx_object(User.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteUser(userId: Long): Boolean =
        "/api/user/$userId".httpDelete()
                .rx_object(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get().result }
                .onErrorReturn { throw it }
                .blockingGet()
}