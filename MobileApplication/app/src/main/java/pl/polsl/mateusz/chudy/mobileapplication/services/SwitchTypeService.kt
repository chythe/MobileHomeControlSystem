package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig

/**
 *
 */
class SwitchTypeService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
        }
    }

    fun getSwitchTypes() {
        TODO()
    }

    fun getSwitchType() {
        TODO()
    }

    fun createSwitchType() {
        TODO()
    }

    fun updateSwitchType() {
        TODO()
    }

    fun deleteSwitchType() {
        TODO()
    }
}