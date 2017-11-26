package pl.polsl.mateusz.chudy.mobileapplication.services

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import pl.polsl.mateusz.chudy.mobileapplication.commands.SwitchCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnectionConfig

/**
 *
 */
class SwitchService {

    init {
        FuelManager.instance.apply {
            basePath = ServerConnectionConfig.getBasePathURLString()
        }
    }

    fun getState() {
        TODO()
    }

    fun switch(switchCommand: SwitchCommand) {
        val gson = Gson()
        val json = gson.toJson(switchCommand)
        "/api/switch".httpPost()
                .header("Content-Type" to "application/json")
                .body(json.toString())
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            print("MC: Failure " + result.get())
                        }
                        is Result.Success -> {
                            print("MC: Success " + result.get())
                        }
                    }
                }
    }
}