package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig

/**
 *
 */
class ModuleConfigurationService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
        }
    }

    fun getModuleConfigurations() {
        TODO()
    }

    fun getModuleConfiguration() {
        TODO()
    }

    fun createModuleConfiguration() {
        TODO()
    }

    fun updateModuleConfiguration() {
        TODO()
    }

    fun deleteModuleConfiguration() {
        TODO()
    }
}
