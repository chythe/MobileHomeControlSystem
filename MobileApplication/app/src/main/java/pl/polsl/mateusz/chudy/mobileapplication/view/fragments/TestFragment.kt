package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.services.UserService
import java.lang.reflect.InvocationTargetException
import java.net.SocketTimeoutException


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TestFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {

    // TODO: Rename and change types of parameters
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
        activity.title = "Test"

        val view = inflater!!.inflate(R.layout.fragment_test, container, false)

        val userService = UserService()

        val getAllButton: View = view.findViewById(R.id.get_all_button)
        getAllButton.setOnClickListener { view ->
            try {
                userService.getUsers()
            } catch (e: Exception) {
                // TODO
                print(e.toString())
            }
        }

        val getButton: View = view!!.findViewById(R.id.get_button)
        getButton.setOnClickListener { view ->
            try {
                userService.getUser(3)
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }

        val createButton: View = view!!.findViewById(R.id.create_button)
        createButton.setOnClickListener { view ->
            try {
                userService.createUser(User(username = "AdrianawDA", password = "dwwqdbseDAvw", role = Role.USER))
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
            }
        }

        val updateButton: View = view!!.findViewById(R.id.update_button)
        updateButton.setOnClickListener { view ->
            userService.updateUser(User(4, "Magda1234", "wadwaDwdawADAdWAWsd", Role.USER))
        }

        val deleteButton: View = view!!.findViewById(R.id.delete_button)
        deleteButton.setOnClickListener { view ->
            userService.deleteUser(6)
        }
        // Inflate the layout for this fragment
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
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
         * @return A new instance of fragment TestFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): TestFragment {
            val fragment = TestFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
