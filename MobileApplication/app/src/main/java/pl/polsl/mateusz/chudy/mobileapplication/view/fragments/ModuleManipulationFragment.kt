package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import kotlinx.android.synthetic.main.fragment_module_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.model.Module


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoomManipulationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RoomManipulationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModuleManipulationFragment : Fragment() {

    private var mModule: Module? = null
    private var mType: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mModule = arguments.getSerializable(ARG_MODULE) as Module
            mType = arguments.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = mType
        val view = inflater!!.inflate(R.layout.fragment_module_manipulation, container, false)
        view.module_manipulation_name_edit_text.setText(mModule!!.name)
        view.module_manipulation_button.text = mType!!.split(" ")[0]
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

        private val ARG_MODULE = "Module"
        private val ARG_TYPE = "Type"

        fun newInstance(module: Module, type: String): ModuleManipulationFragment {
            val fragment = ModuleManipulationFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODULE, module)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
