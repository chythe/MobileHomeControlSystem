package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_search_modules.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Module
import pl.polsl.mateusz.chudy.mobileapplication.api.ModuleApi
import pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters.ModulesAdapter


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchModulesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchModulesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchModulesFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = resources.getString(R.string.search_modules)
        val view = inflater!!.inflate(R.layout.fragment_search_modules, container, false)
        view.search_modules_button.text = activity.title.split(" ")[0]

        view.search_modules_button.setOnClickListener { _ ->
            try {
                showProgress(view, true)
                val modules = ModuleApi.searchUnknownModules()
                view.search_modules_list_view.adapter = ModulesAdapter(modules)
                registerForContextMenu(view.search_modules_list_view)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                showProgress(view, false)
            }
        }

        view.search_modules_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            try {
                val module = parent.getItemAtPosition(position) as Module
                val fragment = ModuleManipulationFragment.newInstance(
                        module, resources.getString(R.string.add_module)) as Fragment
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

    private fun showProgress(view: View, show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

//        if (show) view.search_modules_constraint_layout.visibility = View.GONE else View.VISIBLE

        view.search_modules_progress.visibility = if (show) View.GONE else View.VISIBLE
        view.search_modules_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.search_modules_progress.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        view.search_modules_progress.visibility = if (show) View.VISIBLE else View.GONE
        view.search_modules_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.search_modules_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
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

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): SearchModulesFragment {
            val fragment = SearchModulesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
