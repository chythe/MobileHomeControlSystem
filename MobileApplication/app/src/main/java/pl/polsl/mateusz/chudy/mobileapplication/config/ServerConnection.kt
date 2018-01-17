package pl.polsl.mateusz.chudy.mobileapplication.config

/**
 * Server connection configuration class
 */
class ServerConnection {

    companion object {

        var SERVER_IP: String = "192.168.0.51"

        var SERVER_PORT: Int = 5000

        /**
         * Gets base server URL address
         */
        fun getBasePathURLString(): String {
            return "http://" + SERVER_IP + ":" + SERVER_PORT.toString()
        }
    }
}