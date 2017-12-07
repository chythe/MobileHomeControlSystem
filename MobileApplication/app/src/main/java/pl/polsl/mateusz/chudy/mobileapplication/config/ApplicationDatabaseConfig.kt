package pl.polsl.mateusz.chudy.mobileapplication.config

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pl.polsl.mateusz.chudy.mobileapplication.dao.*
import pl.polsl.mateusz.chudy.mobileapplication.enums.RoleConverter
import pl.polsl.mateusz.chudy.mobileapplication.model.*

/**
 *
 */
@Database(entities = [
    (ModuleConfiguration::class),
    (Module::class),
    (Room::class),
    (SwitchType::class),
    (User::class)],
        version = 2)
@TypeConverters(RoleConverter::class)
abstract class ApplicationDatabaseConfig : RoomDatabase() {

    abstract fun moduleConfigurationDao(): ModuleConfigurationDao

    abstract fun moduleDao(): ModuleDao

    abstract fun roomDao(): RoomDao

    abstract fun switchTypeDao(): SwitchTypeDao

    abstract fun userDao(): UserDao
}