package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig

/**
 *
 */
class UserService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
        }
    }

    fun getUsers() {
        TODO()
    }

    fun getUser() {
        TODO()
    }

    fun createUser() {
        TODO()
    }

    fun updateUser() {
        TODO()
    }

    fun deleteUser() {
        TODO()
    }
}