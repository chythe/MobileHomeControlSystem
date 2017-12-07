package pl.polsl.mateusz.chudy.mobileapplication.dao

import android.arch.persistence.room.*
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType

/**
 *
 */
@Dao
interface SwitchTypeDao {

    @Query("select * from switchType")
    fun getSwitchTypes(): List<SwitchType>

//    @Query("select * from switch_type where switchType_id = :switchTypeId")
//    fun getSwitchType(switchTypeId: Long): SwitchType
//
//    @Insert
//    fun createSwitchType(switchType: SwitchType)
//
//    @Update
//    fun updateSwitchType(switchType: SwitchType)

//    @Query("delete from switch_type where switchType_id = :switchTypeId")
//    fun deleteSwitchType(switchTypeId: Long)

    @Delete
    fun deleteSwitchType(switchType: SwitchType)
}