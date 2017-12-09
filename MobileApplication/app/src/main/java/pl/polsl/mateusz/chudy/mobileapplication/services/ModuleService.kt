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
import pl.polsl.mateusz.chudy.mobileapplication.model.Module

/**
 *
 */
object ModuleService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getModules(): List<Module> =
        "/api/module".httpGet()
                .rx_object(Module.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getModule(moduleId: Long): Module =
        "/api/module/$moduleId".httpGet()
                .rx_object(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createModule(module: Module): Module =
        "/api/module".httpPost()
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module))
                .rx_object(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateModule(module: Module): Module =
        "/api/module".httpPut()
                .body(Gson().toJson(module))
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module))
                .rx_object(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get() }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteModule(moduleId: Long): Boolean =
        "/api/module/$moduleId".httpDelete()
                .rx_object(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map { it -> it.get().result }
                .onErrorReturn { throw it }
                .blockingGet()
}