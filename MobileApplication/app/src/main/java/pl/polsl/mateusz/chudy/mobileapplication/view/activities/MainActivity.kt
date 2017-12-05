package pl.polsl.mateusz.chudy.mobileapplication.view.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import pl.polsl.mateusz.chudy.mobileapplication.R
//import com.jakewharton.rxbinding2.view.RxView
//import com.jakewharton.rxbinding2.widget.RxCompoundButton
//import com.jakewharton.rxbinding2.widget.RxTextView
//import io.reactivex.android.schedulers.AndroidSchedulers
import android.net.Uri
import android.support.v4.app.Fragment
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.view.fragments.*


class MainActivity : AppCompatActivity(),
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
        SearchModulesFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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
            R.id.action_sing_out -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            // Handle navigation view item clicks here.
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_profile -> {
                    fragment = ProfileFragment.newInstance(User(),
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
