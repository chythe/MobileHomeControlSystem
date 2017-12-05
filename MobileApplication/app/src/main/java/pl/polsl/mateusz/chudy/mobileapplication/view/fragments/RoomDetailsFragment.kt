package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Room


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoomDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
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
