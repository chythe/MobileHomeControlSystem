package pl.polsl.mateusz.chudy.mobileapplication.view.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import android.net.Uri
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.services.AuthenticationService
import pl.polsl.mateusz.chudy.mobileapplication.view.fragments.*

/**
 *
 */
class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        UsersFragment.OnFragmentInteractionListener,
        RoomsFragment.OnFragmentInteractionListener,
        SwitchTypesFragment.OnFragmentInteractionListener,
        ConfigurationFragment.OnFragmentInteractionListener,
        UserManipulationFragment.OnFragmentInteractionListener,
        ModulesFragment.OnFragmentInteractionListener,
        RoomDetailsFragment.OnFragmentInteractionListener,
        RoomManipulationFragment.OnFragmentInteractionListener,
        SwitchTypeDetailFragment.OnFragmentInteractionListener,
        SwitchTypeManipulationFragment.OnFragmentInteractionListener,
        SearchModulesFragment.OnFragmentInteractionListener,
        ModuleManipulationFragment.OnFragmentInteractionListener,
        ConfigurationManipulationFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        setImageBackground()
    }

    private fun setImageBackground() {
        val mainImageView: ImageView = findViewById(R.id.content_main_image_view)
        val bitMap = BitmapFactory.decodeStream(resources.assets.open("png/house.png"))
        mainImageView.setImageBitmap(bitMap)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val result = AuthenticationService.logout()
                finish()
                return result
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            var access = true
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_profile -> {
                    val currentUser = AuthenticationService.getCurrentUser()
                    fragment = ProfileFragment.newInstance(currentUser!!,
                            resources.getString(R.string.profile))
                }
                R.id.nav_users -> {
                    if (AuthenticationService.checkPermissions(Role.ADMIN))
                        fragment = UsersFragment::class.java.newInstance()
                    else access = false
                }
                R.id.nav_rooms -> {
                    fragment = RoomsFragment::class.java.newInstance()
                }
                R.id.nav_switch_types -> {
                    fragment = SwitchTypesFragment::class.java.newInstance()
                }
                R.id.nav_modules -> {
                    if (AuthenticationService.checkPermissions(Role.USER))
                        fragment = ModulesFragment::class.java.newInstance()
                    else access = false
                }
            }
            if (access) {
                val fragmentManager = supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit()
            } else {
                Toast.makeText(this, resources.getString(R.string.no_access), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            drawer_layout.closeDrawers()
            return true
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
