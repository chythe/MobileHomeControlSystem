package pl.polsl.mateusz.chudy.mobileapplication.services

import android.content.ContentValues.TAG
import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import pl.polsl.mateusz.chudy.mobileapplication.commands.AcknowledgeCommand
import java.net.SocketTimeoutException


/**
 *
 */
object ModuleConfigurationService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getModuleConfigurations(): List<ModuleConfiguration> =
        "/api/module-configuration".httpGet()
                .rx_object(ModuleConfiguration.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getModuleConfiguration(moduleId: Long, switchNo: Short): ModuleConfiguration =
        "/api/module-configuration/?moduleId=$moduleId&switchNo=$switchNo".httpGet()
                .rx_object(ModuleConfiguration.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createModuleConfiguration(moduleConfiguration: ModuleConfiguration): ModuleConfiguration =
        "/api/module-configuration".httpPost()
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                        .create().toJson(moduleConfiguration))
                .rx_object(ModuleConfiguration.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateModuleConfiguration(moduleConfiguration: ModuleConfiguration): ModuleConfiguration =
        "/api/module-configuration".httpPut()
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                        .create().toJson(moduleConfiguration))
                .rx_object(ModuleConfiguration.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteModuleConfiguration(moduleId: Long, switchNo: Short): Boolean =
        "/api/module-configuration/?moduleId=$moduleId&switchNo=$switchNo".httpDelete()
                .rx_object(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get().result }
                .onErrorReturn { throw it }
                .blockingGet()
}
