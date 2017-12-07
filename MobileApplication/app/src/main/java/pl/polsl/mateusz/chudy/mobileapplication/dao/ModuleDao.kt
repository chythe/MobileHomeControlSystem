package pl.polsl.mateusz.chudy.mobileapplication.dao

import android.arch.persistence.room.*
import pl.polsl.mateusz.chudy.mobileapplication.model.Module

/**
 *
 */
@Dao
interface ModuleDao {

    @Query("select * from module")
    fun getModules(): List<Module>

//    @Query("select * from module where module_id = :moduleId")
//    fun getModule(moduleId: Long): Module
//
//    @Insert
//    fun createModule(module: Module)
//
//    @Update
//    fun updateModule(module: Module)

//    @Query("delete from module where module_id = :moduleId")
//    fun deleteModule(moduleId: Long)

    @Delete
    fun deleteModule(module: Module)
}