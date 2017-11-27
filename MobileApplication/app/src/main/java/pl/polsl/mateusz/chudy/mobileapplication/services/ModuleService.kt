package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig
import pl.polsl.mateusz.chudy.mobileapplication.model.Module

/**
 *
 */
class ModuleService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getModules() {
        "/api/module".httpGet()
                .responseObject(Module.ListDeserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val ms: List<Module> = result.get()
                            print("MC: Success " + ms)
                        }
                    }
                }
    }

    fun getModule(moduleId: Long) {
        "/api/module/$moduleId".httpGet()
                .responseObject(Module.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val m: Module = result.get()
                            print("MC: Success " + m)
                        }
                    }
                }
    }

    fun createModule(module: Module) {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        "/api/module".httpPost()
                .body(gson.toJson(module))
                .responseObject(Module.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val m = result.get()
                            print("MC: Success " + m)
                        }
                    }
                }
    }

    fun updateModule(module: Module) {
        val gson = GsonBuilder().create()
        "/api/module".httpPut()
                .body(gson.toJson(module))
                .responseObject(Module.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val m = result.get()
                            print("MC: Success " + m)
                        }
                    }
                }
    }

    fun deleteModule(moduleId: Long) {
        "/api/module/$moduleId".httpDelete()
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val result: String = result.get()
                            print("MC: Success " + result)
                        }
                    }
                }
    }
}