package pl.polsl.mateusz.chudy.mobileapplication.services

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
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(Module.ListDeserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun getModule(moduleId: Long): Module =
        "/api/module/$moduleId".httpGet()
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun createModule(module: Module): Module =
        "/api/module".httpPost()
                .header("Authorization" to AuthenticationService.getToken())
                .body(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module))
                .rx_responseObject(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun updateModule(module: Module): Module =
        "/api/module".httpPut()
                .header("Authorization" to AuthenticationService.getToken())
                .body(Gson().toJson(module))
                .rx_responseObject(Module.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun deleteModule(moduleId: Long): Boolean =
        "/api/module/$moduleId".httpDelete()
                .header("Authorization" to AuthenticationService.getToken())
                .rx_responseObject(AcknowledgeCommand.Deserializer())
                .subscribeOn(Schedulers.newThread())
                .map {
                    AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                    it.second.component1()!!.result
                }
                .onErrorReturn { throw it }
                .blockingGet()

    fun searchUnknownModules(): List<Module> =
            "/api/module/search".httpGet()
                    .header("Authorization" to AuthenticationService.getToken())
                    .rx_responseObject(Module.ListDeserializer())
                    .subscribeOn(Schedulers.newThread())
                    .map {
                        AuthenticationService.setToken(it.first.headers["Authorization"]!![0])
                        it.second.component1()!!
                    }
                    .onErrorReturn { throw it }
                    .blockingGet()
}