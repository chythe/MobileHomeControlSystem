package pl.polsl.mateusz.chudy.mobileapplication

import android.app.Application
import android.arch.persistence.room.Room
import pl.polsl.mateusz.chudy.mobileapplication.config.ApplicationDatabaseConfig

/**
 *
 */
class MobileHomeApplication : Application() {

    companion object {
        var databaseConfig: ApplicationDatabaseConfig? = null
    }

    override fun onCreate() {
        super.onCreate()
        MobileHomeApplication.databaseConfig =  Room.databaseBuilder(this,
                ApplicationDatabaseConfig::class.java, "mobile_home_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}