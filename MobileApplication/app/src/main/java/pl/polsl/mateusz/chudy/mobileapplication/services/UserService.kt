package pl.polsl.mateusz.chudy.mobileapplication.services

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
import pl.polsl.mateusz.chudy.mobileapplication.model.User

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
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(User.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getUser(userId: Long): User =
        "/api/user/$userId".httpGet()
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(User.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createUser(user: User): User =
        "/api/user".httpPost()
                .header("Authorization" to AuthenticationService.getToken())
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(user))
                .rx_responseObject(User.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateUser(user: User): User =
        "/api/user".httpPut()
                .header("Authorization" to AuthenticationService.getToken())
                .body(Gson().toJson(user))
                .rx_responseObject(User.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteUser(userId: Long): Boolean =
        "/api/user/$userId".httpDelete()
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!.result
                }
                .onErrorReturn { throw it }
                .blockingGet()
}