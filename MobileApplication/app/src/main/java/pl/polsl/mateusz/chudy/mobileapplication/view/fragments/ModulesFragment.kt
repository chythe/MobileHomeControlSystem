package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import pl.polsl.mateusz.chudy.mobileapplication.R
import kotlinx.android.synthetic.main.fragment_modules.view.*
import pl.polsl.mateusz.chudy.mobileapplication.model.Module
import pl.polsl.mateusz.chudy.mobileapplication.services.ModuleService
import pl.polsl.mateusz.chudy.mobileapplication.view.adapters.ModulesAdapter


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ModulesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ModulesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModulesFragment : Fragment() {

    private val MENU_CONTEXT_DELETE_MODULE: Int = 0
    private val MENU_CONTEXT_EDIT_MODULE: Int = 1

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
        activity.title = resources.getString(R.string.modules)
        val view = inflater!!.inflate(R.layout.fragment_modules, container, false)
		try {
			val modules = ModuleService.getModules()
			view.modules_list_view.adapter = ModulesAdapter(modules)
			registerForContextMenu(view.modules_list_view)
		} catch (e: Exception) {
			e.printStackTrace()
		}
        view.modules_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            try {
                val module = parent.getItemAtPosition(position) as Module
                val fragment = ConfigurationFragment.newInstance(
                        module) as Fragment
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        view.modules_list_view.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            if (view === view.modules_list_view) {
                val info = contextMenuInfo as AdapterView.AdapterContextMenuInfo
                val room = view.modules_list_view.adapter.getItem(info.position) as Module
                contextMenu.setHeaderTitle(room.name)
                contextMenu.add(Menu.NONE, MENU_CONTEXT_DELETE_MODULE, Menu.NONE,
                        resources.getString(R.string.delete_module)!!.split(" ")[0])
                contextMenu.add(Menu.NONE, MENU_CONTEXT_EDIT_MODULE, Menu.NONE,
                        resources.getString(R.string.edit_module)!!.split(" ")[0])
            }
        }

        view.modules_add_button.setOnClickListener { view ->
            try {
                val fragment = SearchModulesFragment::class.java.newInstance() as Fragment
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MENU_CONTEXT_DELETE_MODULE -> {
                try {
                    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                    Log.d(TAG, "removing item pos=" + info.position)
                    val adapter = view!!.modules_list_view.adapter as ModulesAdapter
                    val module = adapter.getItem(info.position) as Module
                    ModuleService.deleteModule(module.moduleId)
                    fragmentManager
                            .beginTransaction()
                            .detach(this)
                            .attach(this)
                            .commit()
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }
            MENU_CONTEXT_EDIT_MODULE -> {
                try {
                    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                    Log.d(TAG, "removing item pos=" + info.position)
                    val adapter = view!!.modules_list_view.adapter as ModulesAdapter
                    val module = adapter.getItem(info.position) as Module
                    val fragment = ModuleManipulationFragment.newInstance(
                            module, resources.getString(R.string.edit_module)) as Fragment
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.content_main, fragment)
                            .addToBackStack(null)
                            .commit()
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }
            else -> super.onContextItemSelected(item)
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): ModulesFragment {
            val fragment = ModulesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
