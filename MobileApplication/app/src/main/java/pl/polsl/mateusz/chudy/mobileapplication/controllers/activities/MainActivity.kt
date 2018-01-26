package pl.polsl.mateusz.chudy.mobileapplication.controllers.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onResume() {
        checkSharedPreferences()
        super.onResume()
    }

    override fun onRestart() {
        checkSharedPreferences()
        super.onRestart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = applicationContext
        checkSharedPreferences()
    }

    private fun checkSharedPreferences() {
        val sharedPreferences = applicationContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        val currentUserString = sharedPreferences.getString("currentUser", "")
        if (!token.isEmpty() && !currentUserString.isEmpty()) {
            intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    companion object {

        const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES"

        var mContext: Context? = null

        fun getContext(): Context {
            return mContext!!
        }
    }
}