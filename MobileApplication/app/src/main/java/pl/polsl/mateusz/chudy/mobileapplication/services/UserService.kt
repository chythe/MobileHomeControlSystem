package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import java.lang.reflect.InvocationTargetException
import java.net.SocketTimeoutException

/**
 *
 */
class UserService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    @Throws(SocketTimeoutException::class)
    fun getUsers() {
        "/api/user".httpGet()
                .responseObject(User.ListDeserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val us: List<User> = result.get()
                            print("MC: Success " + us)
//                            return@responseObject us
                        }
                    }
                }
    }

    @Throws(SocketTimeoutException::class)
    fun getUser(userId: Long) {
        "/api/user/$userId".httpGet()
                .responseObject(User.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val u: User = result.get()
                            print("MC: Success " + u)
                        }
                    }
                }
    }

    @Throws(SocketTimeoutException::class)
    fun createUser(user: User) {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        "/api/user".httpPost()
                .body(gson.toJson(user))
                .responseObject(User.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val u: User = result.get()
                            print("MC: Success " + u)
                        }
                    }
                }
    }

    @Throws(SocketTimeoutException::class)
    fun updateUser(user: User) {
        val gson = Gson()
        "/api/user".httpPut()
                .body(gson.toJson(user))
                .responseObject(User.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val u: User = result.get()
                            print("MC: Success " + u)
                        }
                    }
                }
    }

    @Throws(SocketTimeoutException::class)
    fun deleteUser(userId: Long) {
        "/api/user/$userId".httpDelete()
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