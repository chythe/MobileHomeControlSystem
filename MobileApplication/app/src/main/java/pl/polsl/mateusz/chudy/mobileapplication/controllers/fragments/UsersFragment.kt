package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_users.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters.UsersAdapter
import android.widget.AdapterView.AdapterContextMenuInfo
import android.view.*
import android.content.ContentValues.TAG
import android.util.Log
import pl.polsl.mateusz.chudy.mobileapplication.api.UserApi


/**
 * A Users [Fragment] class.
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsersFragment : Fragment() {

    private val MENU_CONTEXT_DELETE_USER: Int = 0

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
        activity.title = resources.getString(R.string.users)
        val view = inflater!!.inflate(R.layout.fragment_users, container, false)
		
		try {
			val users = UserApi.getUsers()
			view.users_list_view.adapter = UsersAdapter(users)
			registerForContextMenu(view.users_list_view)
		} catch (e: Exception) {
			e.printStackTrace()
		}
		
        view.users_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            try {
                val user = parent.getItemAtPosition(position) as User
                val fragment = ProfileFragment.newInstance(
                        user, resources.getString(R.string.user_details)) as Fragment
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        view.users_list_view.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            if (view === view.users_list_view) {
                val info = contextMenuInfo as AdapterContextMenuInfo
                val user = view.users_list_view.adapter.getItem(info.position) as User
                contextMenu.setHeaderTitle(user.username)
                contextMenu.add(Menu.NONE, MENU_CONTEXT_DELETE_USER, Menu.NONE,
                        resources.getString(R.string.delete_user)!!.split(" ")[0])
            }
        }

        view.users_add_button.setOnClickListener { view ->
            try {
                val fragment = UserManipulationFragment.newInstance(
                        User(),
                        resources.getString(R.string.add_user)) as Fragment
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
            MENU_CONTEXT_DELETE_USER -> {
				try {
					val info = item.menuInfo as AdapterContextMenuInfo
					Log.d(TAG, "removing item pos=" + info.position)
					val adapter = view!!.users_list_view.adapter as UsersAdapter
					val user = adapter.getItem(info.position) as User
					UserApi.deleteUser(user.userId)
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
            else -> super.onContextItemSelected(item)
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): UsersFragment {
            val fragment = UsersFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
