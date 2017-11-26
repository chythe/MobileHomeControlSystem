package pl.polsl.mateusz.chudy.mobileapplication.config

/**
 * Created by chyth on 25.11.2017.
 */
class ServerConnectionConfig {

    companion object {

        val SERVER_IP: String = "192.168.0.51"

        val SERVER_PORT: Int = 5000

        fun getBasePathURLString(): String {
            return "http://" + SERVER_IP + ":" + SERVER_PORT.toString()
        }
    }
}