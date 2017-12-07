package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_configuration_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.MobileHomeApplication
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfigurationManipulationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
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
                resources.getString(R.string.switch_number) +
                        mModuleConfiguration!!.switchNo
        view.configuration_manipulation_name_edit_text.setText(mModuleConfiguration!!.name)
        val roomSpinner = view.configuration_manipulation_room_spinner
        roomSpinner.prompt = "Select room"
        val rooms = MobileHomeApplication.databaseConfig?.roomDao()?.getRooms()
        roomSpinner.adapter = ArrayAdapter<Room>(this.activity,
                R.layout.support_simple_spinner_dropdown_item, rooms)
        roomSpinner.setSelection(mModuleConfiguration!!.roomId.toInt())
        val switchTypesSpinner = view.configuration_manipulation_switch_type_spinner
        switchTypesSpinner.prompt = "Select switch type"
        val switchTypes = MobileHomeApplication.databaseConfig?.switchTypeDao()?.getSwitchTypes()
        switchTypesSpinner.adapter = ArrayAdapter<SwitchType>(this.activity,
                R.layout.support_simple_spinner_dropdown_item, switchTypes)
        switchTypesSpinner.setSelection(mModuleConfiguration!!.switchTypeId.toInt())
        view.configuration_manipulation_button.text = mType!!.split(" ")[0]
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
