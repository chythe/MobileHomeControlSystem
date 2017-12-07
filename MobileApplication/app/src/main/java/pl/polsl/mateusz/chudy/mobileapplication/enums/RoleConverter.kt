package pl.polsl.mateusz.chudy.mobileapplication.enums

import android.arch.persistence.room.TypeConverter


/**
 *
 */
class RoleConverter {

    @TypeConverter
    fun fromRole(role: Role?): String? {
        return if (null == role) {
            null
        } else role!!.name

    }

    @TypeConverter
    fun toRole(role: String?): Role? {
        return if (null == role) {
            null
        } else Role.valueOf(role)

    }
}