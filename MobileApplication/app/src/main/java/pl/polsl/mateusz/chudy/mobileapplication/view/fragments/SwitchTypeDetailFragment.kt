package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

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
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType
import pl.polsl.mateusz.chudy.mobileapplication.services.ModuleConfigurationService
import pl.polsl.mateusz.chudy.mobileapplication.services.SwitchTypeService
import pl.polsl.mateusz.chudy.mobileapplication.view.adapters.ModuleConfigurationsAdapter


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
        val moduleConfigurations = SwitchTypeService.getSwitchTypeModuleConfigurations(mSwitchType!!.switchTypeId)
        view.switch_type_details_list_view.adapter = ModuleConfigurationsAdapter(moduleConfigurations)
        registerForContextMenu(view.switch_type_details_list_view)
        view.switch_type_details_list_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val adapter = view!!.switch_type_details_list_view.adapter as ModuleConfigurationsAdapter
            val moduleConfiguration: ModuleConfiguration = adapter.getItem(position) as ModuleConfiguration
            moduleConfiguration.state = !moduleConfiguration.state
            adapter.notifyDataSetChanged()
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
