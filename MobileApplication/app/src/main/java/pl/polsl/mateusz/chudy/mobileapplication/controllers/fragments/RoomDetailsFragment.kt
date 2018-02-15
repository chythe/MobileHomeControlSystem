package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_room_details.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.commands.SwitchCommand
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import pl.polsl.mateusz.chudy.mobileapplication.api.AuthenticationApi
import pl.polsl.mateusz.chudy.mobileapplication.api.RoomApi
import pl.polsl.mateusz.chudy.mobileapplication.api.SwitchApi
import pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters.ModuleConfigurationsAdapter


/**
 * A RoomDetails [Fragment] class.
 * Use the [RoomDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomDetailsFragment : Fragment() {

    private var mRoom: Room? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mRoom = arguments.getSerializable(ARG_ROOM) as Room
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = resources.getString(R.string.room_details)
        var view = inflater!!.inflate(R.layout.fragment_room_details, container, false)
        view.room_details_name_edit_text.setText(mRoom!!.name)

        if (AuthenticationApi.checkPermissions(Role.USER)) {
            view.room_details_edit_button.setOnClickListener { view ->
                try {
                    val fragment = RoomManipulationFragment.newInstance(
                            mRoom!!,
                            resources.getString(R.string.edit_room)) as Fragment
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
            view.room_details_edit_button.visibility = View.INVISIBLE
        }

		try {
			val moduleConfigurations = RoomApi.getRoomModuleConfigurations(mRoom!!.roomId)
            val moduleStates = SwitchApi.getStates()
            for (mc in moduleConfigurations) {
                for (s in moduleStates) {
                    if (mc.moduleId == s.moduleId && mc.switchNo == s.switchNo) {
                        mc.state = s.state
                    }
                }
            }
			view.room_details_list_view.adapter = ModuleConfigurationsAdapter(moduleConfigurations)
			registerForContextMenu(view.room_details_list_view)
		} catch (e: Exception) {
			e.printStackTrace()
		}

        view.room_details_list_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            try {
                val adapter = view!!.room_details_list_view.adapter as ModuleConfigurationsAdapter
                val moduleConfiguration = adapter.getItem(position) as ModuleConfiguration
                val result = SwitchApi.switch(SwitchCommand(
                        moduleConfiguration.moduleId, moduleConfiguration.switchNo, !moduleConfiguration.state))
                if (result)
                    moduleConfiguration.state = !moduleConfiguration.state
                adapter.notifyDataSetChanged()
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

        private val ARG_ROOM = "Room"

        fun newInstance(room: Room): RoomDetailsFragment {
            val fragment = RoomDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_ROOM, room)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
