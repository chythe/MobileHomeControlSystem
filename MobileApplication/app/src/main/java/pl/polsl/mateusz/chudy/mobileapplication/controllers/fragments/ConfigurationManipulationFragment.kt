package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_configuration_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType
import pl.polsl.mateusz.chudy.mobileapplication.api.ModuleConfigurationApi
import pl.polsl.mateusz.chudy.mobileapplication.api.RoomApi
import pl.polsl.mateusz.chudy.mobileapplication.api.SwitchTypeApi


/**
 * A ConfigurationManipulation[Fragment] class.
 * Use the [ConfigurationManipulationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfigurationManipulationFragment : Fragment() {

    private var mModuleConfiguration: ModuleConfiguration? = null
    private var mType: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mModuleConfiguration = arguments.getSerializable(ARG_MOD_CONF) as ModuleConfiguration
            mType = arguments.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = mType
        val view = inflater!!.inflate(R.layout.fragment_configuration_manipulation, container, false)
        view.configuration_manipulation_switch_no_text_view.text =
                resources.getString(R.string.switch_number) + " " +
                        mModuleConfiguration!!.switchNo
        view.configuration_manipulation_name_edit_text.setText(mModuleConfiguration!!.name)

        val roomSpinner = view.configuration_manipulation_room_spinner
        try {
            roomSpinner.prompt = "Select room"
            val rooms = RoomApi.getRooms()
            roomSpinner.adapter = ArrayAdapter<Room>(this.activity,
                    R.layout.support_simple_spinner_dropdown_item, rooms) as SpinnerAdapter?
            roomSpinner.setSelection(mModuleConfiguration!!.roomId.toInt())

            var selectedRoomPosition = 0
            if (mModuleConfiguration!!.roomId.toInt() != 0) {
                val selectedRoom = rooms
                        .filter { st -> st.roomId == mModuleConfiguration!!.roomId }.single()
                selectedRoomPosition = rooms.indexOf(selectedRoom)
            }
            roomSpinner.setSelection(selectedRoomPosition)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val switchTypesSpinner = view.configuration_manipulation_switch_type_spinner
        try {
            switchTypesSpinner.prompt = "Select switch type"
            val switchTypes = SwitchTypeApi.getSwitchTypes()
            switchTypesSpinner.adapter = ArrayAdapter<SwitchType>(this.activity,
                    R.layout.support_simple_spinner_dropdown_item, switchTypes) as SpinnerAdapter?
            var selectedSwitchTypePosition = 0
            if (mModuleConfiguration!!.switchTypeId.toInt() != 0) {
                val selectedSwitchType = switchTypes
                        .filter { st -> st.switchTypeId == mModuleConfiguration!!.switchTypeId }.single()
                selectedSwitchTypePosition = switchTypes.indexOf(selectedSwitchType)
            }
            switchTypesSpinner.setSelection(selectedSwitchTypePosition)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val typeString = mType!!.split(" ")[0]
        view.configuration_manipulation_button.text = typeString
        view.configuration_manipulation_button.setOnClickListener { _ ->
            try {
                val moduleId = mModuleConfiguration!!.moduleId
                val switchNo = mModuleConfiguration!!.switchNo
                val roomId = (roomSpinner.selectedItem as Room).roomId
                val switchTypeId = (switchTypesSpinner.selectedItem as SwitchType).switchTypeId
                val name = view.configuration_manipulation_name_edit_text.text.toString()
                when (typeString.toLowerCase()) {
                    "edit" -> {
                        ModuleConfigurationApi.updateModuleConfiguration(
                                ModuleConfiguration(
                                        moduleId, switchNo, roomId, switchTypeId, name))
                        fragmentManager.popBackStack()
                        Toast.makeText(activity, resources.getString(R.string.configuration_edited), Toast.LENGTH_SHORT).show()
                    }
                    "add" -> {
                        ModuleConfigurationApi.createModuleConfiguration(
                                ModuleConfiguration(
                                        moduleId, switchNo, roomId, switchTypeId, name))
                        fragmentManager.popBackStack()
                        Toast.makeText(activity, resources.getString(R.string.configuration_added), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return view
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_MOD_CONF = "Module Configuration"
        private val ARG_TYPE = "Type"

        fun newInstance(moduleConfiguration: ModuleConfiguration, type: String): ConfigurationManipulationFragment {
            val fragment = ConfigurationManipulationFragment()
            val args = Bundle()
            args.putSerializable(ARG_MOD_CONF, moduleConfiguration)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
