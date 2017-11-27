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
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.services.UserService
import pl.polsl.mateusz.chudy.mobileapplication.services.SwitchService


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val userService = UserService()

        val getAllButton: View = findViewById(R.id.get_all_button)
        getAllButton.setOnClickListener { view ->
            userService.getUsers()
        }

        val getButton: View = findViewById(R.id.get_button)
        getButton.setOnClickListener { view ->
            userService.getUser(3)
        }

        val createButton: View = findViewById(R.id.create_button)
        createButton.setOnClickListener { view ->
            userService.createUser(User(username="Adrian22", password="dstvretbsevdfevw", role="USER"))
        }

        val updateButton: View = findViewById(R.id.update_button)
        updateButton.setOnClickListener { view ->
            userService.updateUser(User(userId = 4, username="Magda", password="wad23q52qdwa1244sd", role="USER"))
        }

        val deleteButton: View = findViewById(R.id.delete_button)
        deleteButton.setOnClickListener { view ->
            userService.deleteUser(6)
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
