package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Module
import android.graphics.*
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_configuration.view.*
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.api.ModuleConfigurationApi


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfigurationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfigurationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfigurationFragment : Fragment() {

    private val SWITCH_COUNT: Int = 6

    private var mModule: Module? = null

    private var mSwitchNo: Short? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mModule = arguments.getSerializable(ARG_MODULE) as Module
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = resources.getString(R.string.module_configurations)
        val view = inflater!!.inflate(R.layout.fragment_configuration, container, false)

        view.configuration_name_edit_text.setText(mModule!!.name)

        val nodemcuImageView: ImageView = view.findViewById(R.id.nodemcu_image_view)
        val bitMap = BitmapFactory.decodeStream(resources.assets.open("png/nodemcu.png"))
        nodemcuImageView.setImageBitmap(bitMap)

        setArrowsColor(view)

        view.configuration_switch0_button.setOnClickListener { _ ->
            mSwitchNo = 0
            setModuleConfigurationInfo(view, mSwitchNo!!)
            setArrowsVisible(mSwitchNo!!, view)
        }
        view.configuration_switch1_button.setOnClickListener { _ ->
            mSwitchNo = 1
            setModuleConfigurationInfo(view, mSwitchNo!!)
            setArrowsVisible(mSwitchNo!!, view)
        }
        view.configuration_switch2_button.setOnClickListener { _ ->
            mSwitchNo = 2
            setModuleConfigurationInfo(view, mSwitchNo!!)
            setArrowsVisible(mSwitchNo!!, view)
        }
        view.configuration_switch3_button.setOnClickListener { _ ->
            mSwitchNo = 3
            setModuleConfigurationInfo(view, mSwitchNo!!)
            setArrowsVisible(mSwitchNo!!, view)
        }
        view.configuration_switch4_button.setOnClickListener { _ ->
            mSwitchNo = 4
            setModuleConfigurationInfo(view, mSwitchNo!!)
            setArrowsVisible(mSwitchNo!!, view)
        }
        view.configuration_switch5_button.setOnClickListener { _ ->
            mSwitchNo = 5
            setModuleConfigurationInfo(view, mSwitchNo!!)
            setArrowsVisible(mSwitchNo!!, view)
        }

        view.configuration_edit_button.setOnClickListener { view ->
            try {
                val moduleConfiguration = ModuleConfigurationApi.getModuleConfiguration(
                        mModule!!.moduleId, mSwitchNo!!)
                val fragment = ConfigurationManipulationFragment.newInstance(
                        moduleConfiguration,
                        resources.getString(R.string.edit_configuration)) as Fragment
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        view.configuration_add_button.setOnClickListener { view ->
            try {
                val fragment = ConfigurationManipulationFragment.newInstance(
                        ModuleConfiguration(moduleId = mModule!!.moduleId, switchNo = mSwitchNo!!),
                        resources.getString(R.string.add_configuration)) as Fragment
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        view.configuration_delete_button.setOnClickListener { view ->
            try {
                val done = ModuleConfigurationApi.deleteModuleConfiguration(
                        moduleId = mModule!!.moduleId, switchNo = mSwitchNo!!)
                if (done) {
                    fragmentManager
                            .beginTransaction()
                            .detach(this)
                            .attach(this)
                            .commit()
                    Toast.makeText(activity, resources.getString(
                            R.string.configuration_deleted), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, resources.getString(
                            R.string.error), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
    }

    private fun setModuleConfigurationInfo(view: View, switchNo: Short) {
        try {
            val moduleConiguration = ModuleConfigurationApi.getModuleConfiguration(mModule!!.moduleId, switchNo)
            view.configuration_name_edit_text.setText(moduleConiguration.name)
            view.configuration_add_button.visibility = View.INVISIBLE
            view.configuration_edit_button.visibility = View.VISIBLE
            view.configuration_delete_button.visibility = View.VISIBLE
        } catch (e: Exception) {
            view.configuration_name_edit_text.setText(resources.getString(R.string.not_defined))
            view.configuration_add_button.visibility = View.VISIBLE
            view.configuration_edit_button.visibility = View.INVISIBLE
            view.configuration_delete_button.visibility = View.INVISIBLE
        }
    }

    private fun setArrowsColor(view: View) {
        view.configuration_output0_icon.setColorFilter(view.resources.getColor(R.color.colorRed))
        view.configuration_output1_icon.setColorFilter(view.resources.getColor(R.color.colorRed))
        view.configuration_output2_icon.setColorFilter(view.resources.getColor(R.color.colorRed))
        view.configuration_output3_icon.setColorFilter(view.resources.getColor(R.color.colorRed))
        view.configuration_output4_icon.setColorFilter(view.resources.getColor(R.color.colorRed))
        view.configuration_output5_icon.setColorFilter(view.resources.getColor(R.color.colorRed))
        view.configuration_input0_icon.setColorFilter(view.resources.getColor(R.color.colorGreen))
        view.configuration_input1_icon.setColorFilter(view.resources.getColor(R.color.colorGreen))
        view.configuration_input2_icon.setColorFilter(view.resources.getColor(R.color.colorGreen))
        view.configuration_input3_icon.setColorFilter(view.resources.getColor(R.color.colorGreen))
        view.configuration_input4_icon.setColorFilter(view.resources.getColor(R.color.colorGreen))
        view.configuration_input5_icon.setColorFilter(view.resources.getColor(R.color.colorGreen))
    }

    private fun setArrowsVisible(switchNo: Short, view: View) {
        val ioArrowsArray: Array<ImageView> = arrayOf(
            view.configuration_output0_icon,
            view.configuration_output1_icon,
            view.configuration_output2_icon,
            view.configuration_output3_icon,
            view.configuration_output4_icon,
            view.configuration_output5_icon,
            view.configuration_input0_icon,
            view.configuration_input1_icon,
            view.configuration_input2_icon,
            view.configuration_input3_icon,
            view.configuration_input4_icon,
            view.configuration_input5_icon
        )
        ioArrowsArray.forEach { v -> v.visibility = View.INVISIBLE }
        ioArrowsArray[switchNo.toInt()].visibility = View.VISIBLE
        ioArrowsArray[switchNo.toInt() + SWITCH_COUNT].visibility = View.VISIBLE
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


        fun newInstance(module: Module): ConfigurationFragment {
            val fragment = ConfigurationFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODULE, module)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
