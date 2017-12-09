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
import kotlinx.android.synthetic.main.fragment_switch_types.view.*
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType
import pl.polsl.mateusz.chudy.mobileapplication.services.SwitchTypeService
import pl.polsl.mateusz.chudy.mobileapplication.view.adapters.SwitchTypesAdapter


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SwitchTypesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SwitchTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SwitchTypesFragment : Fragment() {

    private val MENU_CONTEXT_DELETE_SWITCH_TYPE: Int = 0

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        activity.title = "Switch Types"
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = resources.getString(R.string.switch_types)
        val view = inflater!!.inflate(R.layout.fragment_switch_types, container, false)
        val switchTypes = SwitchTypeService.getSwitchTypes()
        view.switch_types_list_view.adapter = SwitchTypesAdapter(switchTypes)
        registerForContextMenu(view.switch_types_list_view)
        view.switch_types_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        try {
                val switchType = parent.getItemAtPosition(position) as SwitchType
                val fragment = SwitchTypeDetailFragment.newInstance(
                        switchType) as Fragment
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        view.switch_types_list_view.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            if (view === view.switch_types_list_view) {
                val info = contextMenuInfo as AdapterView.AdapterContextMenuInfo
                val switchType = view.switch_types_list_view.adapter.getItem(info.position) as SwitchType
                contextMenu.setHeaderTitle(switchType.name)
                contextMenu.add(Menu.NONE, MENU_CONTEXT_DELETE_SWITCH_TYPE, Menu.NONE,
                        resources.getString(R.string.delete_switch_type)!!.split(" ")[0])
            }
        }

        view.switch_types_add_switch_type_button.setOnClickListener { view ->
            try {
                val fragment = SwitchTypeManipulationFragment.newInstance(
                        SwitchType(),
                        resources.getString(R.string.add_switch_type)) as Fragment
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
            MENU_CONTEXT_DELETE_SWITCH_TYPE -> {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                Log.d(TAG, "removing item pos=" + info.position)
                val adapter = view!!.switch_types_list_view.adapter as SwitchTypesAdapter
                val switchType = adapter.getItem(info.position) as SwitchType
                SwitchTypeService.deleteSwitchType(switchType.switchTypeId)
                fragmentManager
                        .beginTransaction()
                        .detach(this)
                        .attach(this)
                        .commit()
                true
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

        fun newInstance(param1: String, param2: String): SwitchTypesFragment {
            val fragment = SwitchTypesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
