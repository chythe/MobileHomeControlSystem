package pl.polsl.mateusz.chudy.mobileapplication.dao

import android.arch.persistence.room.*
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration

/**
 *
 */
@Dao
interface ModuleConfigurationDao {

    @Query("select * from moduleConfiguration")
    fun getModuleConfigurations(): List<ModuleConfiguration>

//    @Query("select * from module_configuration where" +
//            "module_id = :moduleId and switch_no = :switchNo")
//    fun getModuleConfiguration(moduleId: Long, switchNo: Short): ModuleConfiguration
//
//    @Insert
//    fun createModuleConfiguration(moduleConfiguration: ModuleConfiguration)
//
//    @Update
//    fun updateModuleConfiguration(moduleConfiguration: ModuleConfiguration)

//    @Query("delete from module_configuration where" +
//            "module_id = :moduleId and switch_no = :switchNo")
//    fun deleteModuleConfiguration(moduleId: Long, switchNo: Short)

    @Delete
    fun deleteModuleConfiguration(moduleConfiguration: ModuleConfiguration)
}