package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType

/**
 *
 */
class SwitchTypeService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
            baseHeaders = mapOf("Content-Type" to "application/json")
        }
    }

    fun getSwitchTypes() {
        "/api/switch-type".httpGet()
                .responseObject(SwitchType.ListDeserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val sts: List<SwitchType> = result.get()
                            print("MC: Success " + sts)
                        }
                    }
                }
    }

    fun getSwitchType(switchTypeId: Long) {
        "/api/switch-type/$switchTypeId".httpGet()
                .responseObject(SwitchType.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val st: SwitchType = result.get()
                            print("MC: Success " + st)
                        }
                    }
                }
    }

    fun createSwitchType(switchType: SwitchType) {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        val json = gson.toJson(switchType)
        "/api/switch-type".httpPost()
                .body(json.toString())
                .responseObject(SwitchType.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val st: SwitchType = result.get()
                            print("MC: Success " + st)
                        }
                    }
                }
    }

    fun updateSwitchType(switchType: SwitchType) {
        val gson = Gson()
        val json = gson.toJson(switchType)
        "/api/switch-type".httpPut()
                .body(json.toString())
                .responseObject(SwitchType.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                            result.get()
                        }
                        is Result.Success -> {
                            val st: SwitchType = result.get()
                            print("MC: Success " + st)
                        }
                    }
                }
    }

    fun deleteSwitchType(switchTypeId: Long) {
        "/api/switch-type/$switchTypeId".httpDelete()
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