package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import kotlinx.android.synthetic.main.fragment_module_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.model.Module
import pl.polsl.mateusz.chudy.mobileapplication.services.ModuleService


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
        view.module_manipulation_ip_edit_text.setText(mModule!!.ipAddress)
        view.module_manipulation_name_edit_text.setText(mModule!!.name)

        val typeString = mType!!.split(" ")[0]
        view.module_manipulation_button.text = typeString
        view.module_manipulation_button.setOnClickListener { _ ->
            try {
                when (typeString.toLowerCase()) {
                    "edit" -> {
                        ModuleService.updateModule(
                                Module(mModule!!.moduleId,
                                        view.module_manipulation_name_edit_text.text.toString(),
                                        mModule!!.ipAddress))
                        fragmentManager.popBackStack()
                        Toast.makeText(activity, resources.getString(R.string.module_edited), Toast.LENGTH_SHORT).show()
                    }
                    "add" -> {
                        ModuleService.createModule(
                                Module(name=view.module_manipulation_name_edit_text.text.toString(),
                                        ipAddress=mModule!!.ipAddress))
                        fragmentManager.popBackStack()
                        Toast.makeText(activity, resources.getString(R.string.module_added), Toast.LENGTH_SHORT).show()
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
