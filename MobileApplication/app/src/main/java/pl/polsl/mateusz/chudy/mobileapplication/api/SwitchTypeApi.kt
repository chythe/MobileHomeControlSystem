package pl.polsl.mateusz.chudy.mobileapplication.api

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
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType

/**
 *
 */
object SwitchTypeApi {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getSwitchTypes(): List<SwitchType> =
        "/api/switch-type".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(SwitchType.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    val error = it.second.component2()
                    if (error != null) {
                        throw error
                    } else {
                        AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                        it.second.component1()!!
                    }
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getSwitchType(switchTypeId: Long): SwitchType =
        "/api/switch-type/$switchTypeId".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(SwitchType.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createSwitchType(switchType: SwitchType): SwitchType =
        "/api/switch-type".httpPost()
                .header("Authorization" to AuthenticationApi.getToken())
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(switchType))
                .rx_responseObject(SwitchType.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateSwitchType(switchType: SwitchType): SwitchType =
        "/api/switch-type".httpPut()
                .header("Authorization" to AuthenticationApi.getToken())
                .body(Gson().toJson(switchType))
                .rx_responseObject(SwitchType.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteSwitchType(switchTypeId: Long): Boolean =
        "/api/switch-type/$switchTypeId".httpDelete()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!.result
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getSwitchTypeModuleConfigurations(switchTypeId: Long): List<ModuleConfiguration> =
        "/api/switch-type/module-configuration/$switchTypeId".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(ModuleConfiguration.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()
}