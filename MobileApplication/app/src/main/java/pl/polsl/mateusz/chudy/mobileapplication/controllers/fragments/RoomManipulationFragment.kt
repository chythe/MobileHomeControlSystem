package pl.polsl.mateusz.chudy.mobileapplication.controllers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Room
import kotlinx.android.synthetic.main.fragment_room_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.api.RoomApi


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoomManipulationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RoomManipulationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomManipulationFragment : Fragment() {

    private var mRoom: Room? = null
    private var mType: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mRoom = arguments.getSerializable(ARG_ROOM) as Room
            mType = arguments.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = mType
        val view = inflater!!.inflate(R.layout.fragment_room_manipulation, container, false)
        view.room_manipulation_name_edit_text.setText(mRoom!!.name)
        val typeString = mType!!.split(" ")[0]
        view.room_manipulation_button.text = typeString
        view.room_manipulation_button.setOnClickListener { _ ->
            try {
                when (typeString.toLowerCase()) {
                    "edit" -> {
                        RoomApi.updateRoom(
                                Room(mRoom!!.roomId, view.room_manipulation_name_edit_text.text.toString()))
						fragmentManager.popBackStack()
                        Toast.makeText(activity, resources.getString(R.string.room_edited), Toast.LENGTH_SHORT).show()
                    }
                    "add" -> {
                        RoomApi.createRoom(
                                Room(name = view.room_manipulation_name_edit_text.text.toString()))
						fragmentManager.popBackStack()
                        Toast.makeText(activity, resources.getString(R.string.room_added), Toast.LENGTH_SHORT).show()
                    }
                }
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
        private val ARG_TYPE = "Type"

        fun newInstance(room: Room, type: String): RoomManipulationFragment {
            val fragment = RoomManipulationFragment()
            val args = Bundle()
            args.putSerializable(ARG_ROOM, room)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
