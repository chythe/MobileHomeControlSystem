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
import pl.polsl.mateusz.chudy.mobileapplication.model.Module

/**
 * Object implementing module management REST API requests
 */
object ModuleApi {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnection.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    /**
     * Get all modules request
     */
    fun getModules(): List<Module> =
        "/api/module".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(Module.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    /**
     * Get module request
     */
    fun getModule(moduleId: Long): Module =
        "/api/module/$moduleId".httpGet()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    /**
     * Create module request
     */
    fun createModule(module: Module): Module =
        "/api/module".httpPost()
                .header("Authorization" to AuthenticationApi.getToken())
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module))
                .rx_responseObject(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    /**
     * Update module request
     */
    fun updateModule(module: Module): Module =
        "/api/module".httpPut()
                .header("Authorization" to AuthenticationApi.getToken())
                .body(Gson().toJson(module))
                .rx_responseObject(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    /**
     * Delete module request
     */
    fun deleteModule(moduleId: Long): Boolean =
        "/api/module/$moduleId".httpDelete()
                .header("Authorization" to AuthenticationApi.getToken())
                .rx_responseObject(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!.result
                }
                .onErrorReturn { throw it }
                .blockingGet()

    /**
     * Search unknown modules
     */
    fun searchUnknownModules(): List<Module> =
            "/api/module/search".httpGet()
                    .header("Authorization" to AuthenticationApi.getToken())
                    .rx_responseObject(Module.ListDeserializer())
                    .subscribeOn(Schedulers.newThread())
                    .map {
                        AuthenticationApi.setToken(it.first.headers["Authorization"]!![0])
                        it.second.component1()!!
                    }
                    .onErrorReturn { throw it }
                    .blockingGet()
}
