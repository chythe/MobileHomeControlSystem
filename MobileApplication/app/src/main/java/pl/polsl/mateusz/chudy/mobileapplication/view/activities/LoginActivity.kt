package pl.polsl.mateusz.chudy.mobileapplication.view.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.text.InputFilter
import kotlinx.android.synthetic.main.activity_login.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import pl.polsl.mateusz.chudy.mobileapplication.commands.LoginCommand
import pl.polsl.mateusz.chudy.mobileapplication.config.ServerConnection
import pl.polsl.mateusz.chudy.mobileapplication.services.AuthenticationService
import java.security.MessageDigest


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_constraint_layout.visibility = View.VISIBLE
        setIpAddressFilter()
        login_pass_edit_text.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })
        login_register_button.setOnClickListener { _ ->
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener { attemptLogin() }
    }

    private fun setIpAddressFilter() {
        val ipAddressFilter = InputFilter { source, start, end, dest, dstart, dend ->
            var charSequence: CharSequence? = null
            if (end > start) {
                val destTxt = dest.toString()
                val resultingTxt = destTxt.substring(0, dstart) +
                        source.subSequence(start, end) +
                        destTxt.substring(dend)
                if (!resultingTxt
                        .matches(Regex("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?"))) {
                    charSequence = ""
                } else {
                    val splits = resultingTxt.split(".")
                    for (value: String in splits) {
                        if (!value.isEmpty() && Integer.valueOf(value) > 255) {
                            charSequence = ""
                        }
                    }
                }
            }
            charSequence
        }
        login_server_ip_edit_text.filters = arrayOf(ipAddressFilter)
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        login_server_ip_edit_text.error = null
        login_server_port_edit_text.error = null
        login_pass_edit_text.error = null
        login_username_edit_text.error = null
        login_pass_edit_text.error = null

        // Store values at the time of the login attempt.
        val serverIpStr = login_server_ip_edit_text.text.toString()
        val serverPortStr = login_server_port_edit_text.text.toString()
        val usernameStr = login_username_edit_text.text.toString()
        val passwordStr = login_pass_edit_text.text.toString()

        var cancel = false
        var focusView: View? = null

        if (!serverIpStr.isEmpty()) {
            ServerConnection.SERVER_IP = serverIpStr
        }

        if (!serverPortStr.isEmpty()) {
            try {
                ServerConnection.SERVER_PORT = serverPortStr.toInt()
            } catch (e: NumberFormatException ) {
                cancel = true
            }
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            login_pass_edit_text.error = getString(R.string.error_invalid_password)
            focusView = login_pass_edit_text
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(usernameStr)) {
            login_username_edit_text.error = getString(R.string.error_field_required)
            focusView = login_username_edit_text
            cancel = true
        } else if (!isUsernameValid(usernameStr)) {
            login_username_edit_text.error = getString(R.string.error_invalid_username)
            focusView = login_username_edit_text
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            mAuthTask = UserLoginTask(usernameStr, passwordStr)
            mAuthTask!!.execute(null as Void?)
        }
    }

    private fun isUsernameValid(username: String): Boolean {
        return username.length >= 4
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 4
    }


    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        login_constraint_layout.visibility = View.GONE

        login_progress.visibility = if (show) View.GONE else View.VISIBLE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val username: String, private val password: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                val bytes = password.toByteArray()
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(bytes)
                val cryptPassword = digest.fold("", { str, it -> str + "%02x".format(it) })
                return AuthenticationService.login(LoginCommand(username, cryptPassword))
            } catch (e: Exception) {
                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            showProgress(false)

            if (success!!) {
                finish()
            } else {
                login_pass_edit_text.error = getString(R.string.error_incorrect_password)
                login_pass_edit_text.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }
}
