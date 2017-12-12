package pl.polsl.mateusz.chudy.mobileapplication.services

import android.widget.Switch
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.rx.rx_responseObject
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

    fun getStates(): List<SwitchCommand> =
        "/api/switch".httpGet()
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(SwitchCommand.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun switch(switchCommand: SwitchCommand): Boolean =
        "/api/switch".httpPost()
                .header("Authorization" to AuthenticationService.getToken())
                .body(Gson().toJson(switchCommand))
                .rx_responseObject(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!.result
                }
                .onErrorReturn { throw it }
                .blockingGet()
}