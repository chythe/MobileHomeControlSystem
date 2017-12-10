package pl.polsl.mateusz.chudy.mobileapplication.view.activities

import android.content.Context
import android.content.Intent
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
//import com.jakewharton.rxbinding2.view.RxView
//import com.jakewharton.rxbinding2.widget.RxCompoundButton
//import com.jakewharton.rxbinding2.widget.RxTextView
//import io.reactivex.android.schedulers.AndroidSchedulers
import android.net.Uri
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.google.gson.Gson
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.services.AuthenticationService
import pl.polsl.mateusz.chudy.mobileapplication.view.fragments.*


class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        UsersFragment.OnFragmentInteractionListener,
        RoomsFragment.OnFragmentInteractionListener,
        SwitchTypesFragment.OnFragmentInteractionListener,
        ConfigurationFragment.OnFragmentInteractionListener,
        TestFragment.OnFragmentInteractionListener,
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
//        val display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//        val scaledBitmap = Bitmap.createScaledBitmap(bitMap, size.x, size.y, true)
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
            // Handle navigation view item clicks here.
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_profile -> {
                    val currentUser = AuthenticationService.getCurrentUser()
                    fragment = ProfileFragment.newInstance(currentUser!!,
                            resources.getString(R.string.profile))
                }
                R.id.nav_users -> {
                    fragment = UsersFragment::class.java.newInstance()
                }
                R.id.nav_rooms -> {
                    fragment = RoomsFragment::class.java.newInstance()
                }
                R.id.nav_switch_types -> {
                    fragment = SwitchTypesFragment::class.java.newInstance()
                }
                R.id.nav_modules -> {
                    fragment = ModulesFragment::class.java.newInstance()
                }
                R.id.nav_test -> {
                    fragment = TestFragment::class.java.newInstance()
                }
            }
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            drawer_layout.closeDrawers()
            return true
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
//        public void onFragmentInteraction(Uri uri);
    }
}
