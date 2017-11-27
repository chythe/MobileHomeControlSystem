package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.core.Encoding
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import java.util.Arrays.asList


/**
 *
 */
class ModuleConfigurationService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getModuleConfigurations() {
        "/api/module-configuration".httpGet()
                .responseObject(ModuleConfiguration.ListDeserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val mcs: List<ModuleConfiguration> = result.get()
                            print("MC: Success " + mcs)
                        }
                    }
                }
    }

    fun getModuleConfiguration(moduleId: Long, switchNo: Short) {
        "/api/module-configuration/module_id=$moduleId&switch_no=$switchNo".httpGet()
                .responseObject(ModuleConfiguration.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val mc: ModuleConfiguration = result.get()
                            print("MC: Success " + mc)
                        }
                    }
                }
    }

    fun createModuleConfiguration(moduleConfiguration: ModuleConfiguration) {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        "/api/module-configuration".httpPost()
                .body(gson.toJson(moduleConfiguration))
                .responseObject(ModuleConfiguration.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val mc = result.get()
                            print("MC: Success " + mc)
                        }
                    }

                }
    }

    fun updateModuleConfiguration(moduleConfiguration: ModuleConfiguration) {
        val gson = GsonBuilder().create()
        "/api/module-configuration".httpPut()
                .body(gson.toJson(moduleConfiguration))
                .responseObject(ModuleConfiguration.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val mc = result.get()
                            print("MC: Success " + mc)
                        }
                    }

                }
    }

    fun deleteModuleConfiguration(moduleId: Long, switchNo: Short) {
        "/api/module-configuration/module_id=$moduleId&switch_no=$switchNo".httpDelete()
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
