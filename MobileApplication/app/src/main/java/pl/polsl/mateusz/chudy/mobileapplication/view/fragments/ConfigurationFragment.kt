package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
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
import com.larvalabs.svgandroid.SVGBuilder
import com.larvalabs.svgandroid.SVG
import java.io.BufferedReader
import java.io.InputStreamReader
import android.graphics.Bitmap
import android.graphics.Canvas
import android.content.Intent
import pl.polsl.mateusz.chudy.mobileapplication.R.mipmap.ic_launcher




/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfigurationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfigurationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfigurationFragment : Fragment() {

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

        val assetManager: AssetManager  = resources.assets
//        val svg = SVGBuilder()
//                .readFromAsset(assetManager, "svg/nodemcu.svg")
//                .build()
//        val canvas = Canvas(Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888))
//        canvas.drawPicture(svg.picture)
//        view.draw(canvas)
//        nodemcuImageView.setImageDrawable(svg.drawable)

        val bitMap = BitmapFactory.decodeStream(assetManager.open("png/nodemcu.png"))
        nodemcuImageView.setImageBitmap(bitMap)

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


        fun newInstance(module: Module): ConfigurationFragment {
            val fragment = ConfigurationFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODULE, module)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
