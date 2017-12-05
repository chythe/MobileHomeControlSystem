package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType


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
        val view = inflater!!.inflate(R.layout.fragment_switch_type_detail, container, false)
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
