package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
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
object SwitchTypeService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getSwitchTypes(): List<SwitchType> =
        "/api/switch-type".httpGet()
                .rx_object(SwitchType.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getSwitchType(switchTypeId: Long): SwitchType =
        "/api/switch-type/$switchTypeId".httpGet()
                .rx_object(SwitchType.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createSwitchType(switchType: SwitchType): SwitchType =
        "/api/switch-type".httpPost()
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(switchType))
                .rx_object(SwitchType.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateSwitchType(switchType: SwitchType): SwitchType =
        "/api/switch-type".httpPut()
                .body(Gson().toJson(switchType))
                .rx_object(SwitchType.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteSwitchType(switchTypeId: Long): Boolean =
        "/api/switch-type/$switchTypeId".httpDelete()
                .rx_object(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get().result }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getSwitchTypeModuleConfigurations(switchTypeId: Long): List<ModuleConfiguration> =
            "/api/switch-type/module-configuration/$switchTypeId".httpGet()
                    .rx_object(ModuleConfiguration.ListDeserializer())
                    .subscribeOn(Schedulers.newThread())
                    .map { it -> it.get() }
                    .onErrorReturn { throw it }
                    .blockingGet()
}