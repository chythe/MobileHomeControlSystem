package pl.polsl.mateusz.chudy.mobileapplication.commands

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class SwitchCommand(
        @SerializedName("module_id") val moduleId: Long,
        @SerializedName("switch_no") val switchNo: Short,
        @SerializedName("state") val state: Boolean
)
