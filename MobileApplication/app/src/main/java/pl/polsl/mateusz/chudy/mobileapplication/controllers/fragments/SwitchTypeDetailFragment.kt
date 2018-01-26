package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_switch_type_detail.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.commands.SwitchCommand
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType
import pl.polsl.mateusz.chudy.mobileapplication.api.AuthenticationApi
import pl.polsl.mateusz.chudy.mobileapplication.api.SwitchApi
import pl.polsl.mateusz.chudy.mobileapplication.api.SwitchTypeApi
import pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters.ModuleConfigurationsAdapter


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SwitchTypeDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SwitchTypeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SwitchTypeDetailFragment : Fragment() {

    private var mSwitchType: SwitchType? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mSwitchType = arguments.getSerializable(ARG_SWITCH_TYPE) as SwitchType
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = resources.getString(R.string.switch_type_details)
        val view = inflater!!.inflate(R.layout.fragment_switch_type_detail, container, false)
        view.switch_type_details_name_edit_text.setText(mSwitchType!!.name)

        if (AuthenticationApi.checkPermissions(Role.USER)) {
            view.switch_type_details_edit_button.setOnClickListener { view ->
                try {
                    val fragment = SwitchTypeManipulationFragment.newInstance(
                            mSwitchType!!,
                            resources.getString(R.string.edit_switch_type)) as Fragment
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.content_main, fragment)
                            .addToBackStack(null)
                            .commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            view.switch_type_details_edit_button.visibility = View.INVISIBLE
        }
		
		try {
			val moduleConfigurations = SwitchTypeApi.getSwitchTypeModuleConfigurations(mSwitchType!!.switchTypeId)
            val moduleStates = SwitchApi.getStates()
            for (mc in moduleConfigurations) {
                for (s in moduleStates) {
                    if (mc.moduleId == s.moduleId && mc.switchNo == s.switchNo) {
                        mc.state = s.state
                    }
                }
            }
			view.switch_type_details_list_view.adapter = ModuleConfigurationsAdapter(moduleConfigurations)
			registerForContextMenu(view.switch_type_details_list_view)
		} catch (e: Exception) {
			e.printStackTrace()
		}
		
        view.switch_type_details_list_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            try {
                val adapter = view!!.switch_type_details_list_view.adapter as ModuleConfigurationsAdapter
                val moduleConfiguration: ModuleConfiguration = adapter.getItem(position) as ModuleConfiguration
                val result = SwitchApi.switch(SwitchCommand(
                        moduleConfiguration.moduleId, moduleConfiguration.switchNo, !moduleConfiguration.state))
                if (result)
                    moduleConfiguration.state = !moduleConfiguration.state
                adapter.notifyDataSetChanged()
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

        private val ARG_SWITCH_TYPE = "Switch Type"


        fun newInstance(switchType: SwitchType): SwitchTypeDetailFragment {
            val fragment = SwitchTypeDetailFragment()
            val args = Bundle()
            args.putSerializable(ARG_SWITCH_TYPE, switchType)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
