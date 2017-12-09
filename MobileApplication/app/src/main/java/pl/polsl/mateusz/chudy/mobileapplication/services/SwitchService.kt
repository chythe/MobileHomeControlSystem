package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.rx.rx_object
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import pl.polsl.mateusz.chudy.mobileapplication.commands.AcknowledgeCommand
import pl.polsl.mateusz.chudy.mobileapplication.commands.SwitchCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection

/**
 *
 */
object SwitchService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getStates(): SwitchCommand =
        "/api/switch".httpGet()
                .rx_object(SwitchCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun switch(switchCommand: SwitchCommand): Boolean =
        "/api/switch".httpPost()
                .body(Gson().toJson(switchCommand))
                .rx_object(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get().result }
                .onErrorReturn { throw it }
                .blockingGet()
}