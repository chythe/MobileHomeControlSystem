package pl.polsl.mateusz.chudy.mobileapplication.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.commands.LoginCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.services.AuthenticationService
import java.security.MessageDigest

/**
 *
 */
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            attemptRegister()
        }
    }

    private fun attemptRegister() {
        title = resources.getString(R.string.action_register)
        val serverIpStr = register_server_ip_edit_text.text.toString()
        val serverPortStr = register_server_port_edit_text.text.toString()
        val usernameStr = register_username_edit_text.text.toString()
        val passwordStr = register_pass_edit_text.text.toString()
        val retypedPasswordStr = register_retype_pass_edit_text.text.toString()

        var cancel = false

        if (serverIpStr.isEmpty()) {
            cancel = true
        }

        if (serverPortStr.isEmpty()) {
            cancel = true
        }

        if (usernameStr.isEmpty()) {
            cancel = false
        }

        if (passwordStr.isEmpty()) {
            cancel = false
        }

        if (retypedPasswordStr.isEmpty()) {
            cancel = false
        }

        if (retypedPasswordStr != passwordStr) {
            cancel = false
        }

        if (!cancel) {
            try {
                ServerConnection.SERVER_IP = serverIpStr
                ServerConnection.SERVER_PORT = serverPortStr.toInt()
                registrationProcess(usernameStr, passwordStr)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

    private fun registrationProcess(username: String, password: String) {
        try {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val cryptPassword = digest.fold("", { str, it -> str + "%02x".format(it) })
            val user = AuthenticationService.register(LoginCommand(username, cryptPassword))
            finish()
            Toast.makeText(this, user.username + " "
                    + resources.getString(R.string.user_registered), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
