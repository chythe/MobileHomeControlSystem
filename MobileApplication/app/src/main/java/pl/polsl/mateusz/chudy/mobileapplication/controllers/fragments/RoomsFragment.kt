package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters.RoomsAdapter
import kotlinx.android.synthetic.main.fragment_rooms.view.*
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.api.AuthenticationApi
import pl.polsl.mateusz.chudy.mobileapplication.api.RoomApi


/**
 * A Rooms [Fragment] class.
 * Use the [RoomsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomsFragment : Fragment() {

    private val MENU_CONTEXT_DELETE_ROOM: Int = 0

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = resources.getString(R.string.rooms)
        val view = inflater!!.inflate(R.layout.fragment_rooms, container, false)
		
		try {
			val rooms = RoomApi.getRooms()
			val adapter = RoomsAdapter(rooms)
			view.rooms_list_view.adapter = adapter
			registerForContextMenu(view.rooms_list_view)
		} catch (e: Exception) {
			e.printStackTrace()
		}

        view.rooms_list_view.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
            try {
                val room = parent.getItemAtPosition(position) as Room
                val fragment = RoomDetailsFragment.newInstance(
                        room) as Fragment
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (AuthenticationApi.checkPermissions(Role.USER)) {

            view.rooms_list_view.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
                if (view === view.rooms_list_view) {
                    val info = contextMenuInfo as AdapterView.AdapterContextMenuInfo
                    val room = view.rooms_list_view.adapter.getItem(info.position) as Room
                    contextMenu.setHeaderTitle(room.name)
                    contextMenu.add(Menu.NONE, MENU_CONTEXT_DELETE_ROOM, Menu.NONE,
                            resources.getString(R.string.delete_room)!!.split(" ")[0])
                }
            }

            view.rooms_add_button.setOnClickListener { view ->
                try {
                    val fragment = RoomManipulationFragment.newInstance(
                            Room(0),
                            resources.getString(R.string.add_room)) as Fragment
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.content_main, fragment)
                            .addToBackStack(null)
                            .commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            view.rooms_add_button.visibility = View.INVISIBLE
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
            MENU_CONTEXT_DELETE_ROOM -> {
				try {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                Log.d(TAG, "removing item pos=" + info.position)
                val adapter = view!!.rooms_list_view.adapter as RoomsAdapter
                val room = adapter.getItem(info.position) as Room
                RoomApi.deleteRoom(room.roomId)
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

        fun newInstance(param1: String, param2: String): RoomsFragment {
            val fragment = RoomsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
