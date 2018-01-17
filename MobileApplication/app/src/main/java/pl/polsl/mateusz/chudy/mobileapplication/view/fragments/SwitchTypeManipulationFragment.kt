package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_switch_type_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType
import pl.polsl.mateusz.chudy.mobileapplication.api.SwitchTypeApi


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SwitchTypeManipulationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SwitchTypeManipulationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SwitchTypeManipulationFragment : Fragment() {

    private var mSwitchType: SwitchType? = null
    private var mType: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mSwitchType = arguments.getSerializable(ARG_SWITCH_TYPE) as SwitchType
            mType = arguments.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = mType
        val view = inflater!!.inflate(R.layout.fragment_switch_type_manipulation, container, false)
        view.switch_type_manipulation_name_edit_text.setText(mSwitchType!!.name)
        val typeString = mType!!.split(" ")[0]
        view.switch_type_manipulation_button.text = typeString

        view.switch_type_manipulation_button.setOnClickListener { _ ->
            try {
                when (typeString.toLowerCase()) {
                    "edit" -> {
                        SwitchTypeApi.updateSwitchType(
                                SwitchType(mSwitchType!!.switchTypeId,
                                        view.switch_type_manipulation_name_edit_text.text.toString()))
								fragmentManager.popBackStack()
                                Toast.makeText(activity, resources.getString(R.string.switch_type_edited), Toast.LENGTH_SHORT).show()
                    }
                    "add" -> {
                        SwitchTypeApi.createSwitchType(
                                SwitchType(
                                        name = view.switch_type_manipulation_name_edit_text.text.toString()))
								fragmentManager.popBackStack()
                                Toast.makeText(activity, resources.getString(R.string.switch_type_added), Toast.LENGTH_SHORT).show()
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

        private val ARG_SWITCH_TYPE = "Switch Type"
        private val ARG_TYPE = "Type"

        fun newInstance(switchType: SwitchType, type: String): SwitchTypeManipulationFragment {
            val fragment = SwitchTypeManipulationFragment()
            val args = Bundle()
            args.putSerializable(ARG_SWITCH_TYPE, switchType)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
