package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import pl.polsl.mateusz.chudy.mobileapplication.R
import kotlinx.android.synthetic.main.fragment_switch_types.view.*
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType
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

    private val MENU_CONTEXT_DELETE_ID: Int = 1

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
        view.switch_types_list_view.adapter = SwitchTypesAdapter()
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
//        view.switch_types_list_view.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            //            if (view === view.rooms_list_view) {
//                val info = contextMenuInfo as AdapterView.AdapterContextMenuInfo
//                val room = view.rooms_list_view.adapter.getItem(info.position) as Room
//                contextMenu.setHeaderTitle(user.username)
//                contextMenu.add(Menu.NONE, MENU_CONTEXT_DELETE_ID, Menu.NONE,
//                        resources.getString(R.string.delete_user)!!.split(" ")[0])
//            }
//        }

//        view.users_list_view.onContextItemSelected

//        view.switch_types_list_view.setOnClickListener { view ->
            //            try {
//                val fragment = UserManipulationFragment.newInstance(
//                        User(0, "", "", Role.USER),
//                        resources.getString(R.string.add_user)) as Fragment
//                fragmentManager
//                        .beginTransaction()
//                        .replace(R.id.content_main, fragment)
//                        .addToBackStack(null)
//                        .commit()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SwitchTypesFragment.
         */
        // TODO: Rename and change types and number of parameters
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
