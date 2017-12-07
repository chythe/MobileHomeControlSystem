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
import android.graphics.drawable.Drawable
import android.R.raw
import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.InputStreamReader
import android.content.Intent
import android.graphics.*
import kotlinx.android.synthetic.main.fragment_configuration.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R.mipmap.ic_launcher
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfigurationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfigurationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfigurationFragment : Fragment() {

    private final val SWITCH_COUNT: Int = 6

    private var mModule: Module? = null

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

        val nodemcuImageView: ImageView = view.findViewById(R.id.nodemcu_image_view)
        val bitMap = BitmapFactory.decodeStream(resources.assets.open("png/nodemcu.png"))

        view.configuration_name_edit_text.setText(resources.getString(R.string.not_defined))
        view.configuration_action_button.text = resources.getString(R.string.add_configuration)
        nodemcuImageView.setImageBitmap(bitMap)

        setArrowsColor(view)

        view.configuration_switch0_button.setOnClickListener { _ ->
            setArrowsVisible(0, view)
        }
        view.configuration_switch1_button.setOnClickListener { _ ->
            setArrowsVisible(1, view)
        }
        view.configuration_switch2_button.setOnClickListener { _ ->
            setArrowsVisible(2, view)
        }
        view.configuration_switch3_button.setOnClickListener { _ ->
            setArrowsVisible(3, view)
        }
        view.configuration_switch4_button.setOnClickListener { _ ->
            setArrowsVisible(4, view)
        }
        view.configuration_switch5_button.setOnClickListener { _ ->
            setArrowsVisible(5, view)
        }

        view.configuration_action_button.setOnClickListener { view ->
            try {
                val fragment = ConfigurationManipulationFragment.newInstance(
                        ModuleConfiguration(),
                        resources.getString(R.string.add_configuration)) as Fragment
//                resources.getString(R.string.edit_configuration)
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
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

    private fun setArrowsVisible(switchNo: Int, view: View) {
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
        ioArrowsArray[switchNo].visibility = View.VISIBLE
        ioArrowsArray[switchNo + SWITCH_COUNT].visibility = View.VISIBLE
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
