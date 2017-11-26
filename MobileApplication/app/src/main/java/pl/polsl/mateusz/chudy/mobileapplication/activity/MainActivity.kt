package pl.polsl.mateusz.chudy.mobileapplication.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import android.content.Intent
import android.view.View
import pl.polsl.mateusz.chudy.mobileapplication.commands.SwitchCommand
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.services.RoomService
import pl.polsl.mateusz.chudy.mobileapplication.services.SwitchService


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

//    private val get_rooms_button: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val blueSwitchOnButton: View = findViewById(R.id.blue_switch_on_button)
        blueSwitchOnButton.setOnClickListener { view ->
            val switchService = SwitchService()
            switchService.switch(SwitchCommand(0, 1, true))
        }

        val blueSwitchOffButton: View = findViewById(R.id.blue_switch_off_button)
        blueSwitchOffButton.setOnClickListener { view ->
            val switchService = SwitchService()
            switchService.switch(SwitchCommand(0, 1, false))
        }

        val redSwitchOnButton: View = findViewById(R.id.red_switch_on_button)
        redSwitchOnButton.setOnClickListener { view ->
            val switchService = SwitchService()
            switchService.switch(SwitchCommand(0, 0, true))
        }

        val redSwitchOffButton: View = findViewById(R.id.red_switch_off_button)
        redSwitchOffButton.setOnClickListener { view ->
            val switchService = SwitchService()
            switchService.switch(SwitchCommand(0, 0, false))
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                // Handle the camera action
            }
            R.id.nav_users -> {

            }
            R.id.nav_rooms -> {

            }
            R.id.nav_configuration -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
