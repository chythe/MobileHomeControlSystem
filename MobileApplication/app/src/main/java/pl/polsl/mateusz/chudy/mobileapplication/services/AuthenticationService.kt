package pl.polsl.mateusz.chudy.mobileapplication.services

import android.content.Context
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_response
import com.github.kittinunf.fuel.rx.rx_responseObject
import com.github.kittinunf.fuel.rx.rx_responseString
import com.github.kittinunf.result.getOrElse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import pl.polsl.mateusz.chudy.mobileapplication.commands.LoginCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.view.activities.MainActivity

/**
 *
 */
object AuthenticationService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun login(loginCommand: LoginCommand): Boolean =
            "/api/authentication/login".httpPost()
                    .body(Gson().toJson(loginCommand))
                    .rx_responseObject(User.Deserializer())
                    .subscribeOn(Schedulers.newThread())
                    .map {
                        setToken(it.first.headers["Authorization"]!![0])
                        setCurrentUser(it.second.component1()!!)
                        true
                    }
                    .onErrorReturn { throw it }
                    .blockingGet()

    fun logout(): Boolean {
        val sharedPreferences = MainActivity.getContext()
                .getSharedPreferences(MainActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val sharedPreferencesEdit = sharedPreferences.edit()
        sharedPreferencesEdit.putString("token", "")
        sharedPreferencesEdit.putString("currentUser", "")
        sharedPreferencesEdit.apply()
        return true
    }

    fun register(loginCommand: LoginCommand): User =
            "/api/authentication/register".httpPost()
                    .body(Gson().toJson(loginCommand))
                    .rx_object(User.Deserializer())
                    .subscribeOn(Schedulers.newThread())
                    .map { it -> it.get() }
                    .onErrorReturn { throw it }
                    .blockingGet()

    fun checkPermissions(role: Role): Boolean {
        val sharedPreferences = MainActivity.getContext()
                .getSharedPreferences(MainActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val currentUserJsonString = sharedPreferences.getString("currentUser", "")
        if (!currentUserJsonString.isEmpty()) {
            val currentUser = Gson().fromJson(currentUserJsonString, User::class.java)
            if (role <= currentUser.role)
                return true
        }
        return false
    }

    fun getToken(): String {
        val sharedPreferences = MainActivity.getContext()
                .getSharedPreferences(MainActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        return token
    }

    fun setToken(token: String) {
        val sharedPreferences = MainActivity.getContext()
                .getSharedPreferences(MainActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val sharedPreferencesEdit = sharedPreferences.edit()
        sharedPreferencesEdit.putString("token", token)
        sharedPreferencesEdit.apply()
    }

    fun getCurrentUser(): User? {
        val sharedPreferences = MainActivity.getContext()
                .getSharedPreferences(MainActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val userJsonString = sharedPreferences.getString("currentUser", "")
        if (!userJsonString.isEmpty())
            return Gson().fromJson(userJsonString, User::class.java)
        return null
    }

    fun setCurrentUser(user: User) {
        val sharedPreferences = MainActivity.getContext()
                .getSharedPreferences(MainActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val sharedPreferencesEdit = sharedPreferences.edit()
        sharedPreferencesEdit.putString("currentUser", Gson().toJson(user))
        sharedPreferencesEdit.apply()
    }
}