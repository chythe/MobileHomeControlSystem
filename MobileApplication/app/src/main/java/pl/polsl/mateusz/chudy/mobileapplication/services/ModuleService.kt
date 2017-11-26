package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig

/**
 *
 */
class ModuleService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
        }
    }

    fun getModules() {
        TODO()
    }

    fun getModule() {
        TODO()
    }

    fun createModule() {
        TODO()
    }

    fun updateModule() {
        TODO()
    }

    fun deleteModule() {
        TODO()
    }
}