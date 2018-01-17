package pl.polsl.mateusz.chudy.mobileapplication.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_user_manipulation.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.User
import pl.polsl.mateusz.chudy.mobileapplication.api.AuthenticationApi
import pl.polsl.mateusz.chudy.mobileapplication.api.UserApi
import java.security.MessageDigest


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserManipulationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserManipulationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserManipulationFragment: Fragment() {

    private var mUser: User? = null
    private var mType: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mUser = arguments.getSerializable(ARG_USER) as User
            mType = arguments.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity.title = mType
        val view = inflater!!.inflate(R.layout.fragment_user_manipulation, container, false)
        view.user_manipulation_username_edit_text.setText(mUser!!.username)
//        view.user_manipulation_pass_edit_text
//        view.user_manipulation_retype_pass_edit_text
        val roleSpinner = view.user_manipulation_role_spinner
        roleSpinner.prompt = "Select user role"
        roleSpinner.adapter = ArrayAdapter<Role>(this.activity, R.layout.support_simple_spinner_dropdown_item, Role.values())
        roleSpinner.setSelection(mUser!!.role.ordinal)
        val typeString = mType!!.split(" ")[0]
        view.user_manipulation_button.text = typeString
        view.user_manipulation_button.setOnClickListener { _ ->
            try {
                val password = view.user_manipulation_pass_edit_text.text.toString()
                val retypedPassword = view.user_manipulation_retype_pass_edit_text.text.toString()
                var cryptPassword = ""
                if (password == retypedPassword) {
                    if (!password.isEmpty()) {
                        val bytes = password.toByteArray()
                        val md = MessageDigest.getInstance("SHA-256")
                        val digest = md.digest(bytes)
                        cryptPassword = digest.fold("", { str, it -> str + "%02x".format(it) })
                    }
                    val role = roleSpinner.selectedItem as Role
                    when (typeString.toLowerCase()) {
                        "edit" -> {
                            UserApi.updateUser(
                                    User(
                                            mUser!!.userId,
                                            view.user_manipulation_username_edit_text.text.toString(),
                                            cryptPassword,
                                            role
                                    ))
							fragmentManager.popBackStack()
                            Toast.makeText(activity, resources.getString(R.string.user_edited), Toast.LENGTH_SHORT).show()
                        }
                        "add" -> {
                            UserApi.createUser(
                                    User(
                                            username = view.user_manipulation_username_edit_text.text.toString(),
                                            password = cryptPassword,
                                            role = role
                                    ))
							fragmentManager.popBackStack()
                            Toast.makeText(activity, resources.getString(R.string.user_added), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(activity, resources.getString(R.string.different_passwords), Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (!AuthenticationApi.checkPermissions(Role.ADMIN))
            view.user_manipulation_role_spinner.visibility = View.INVISIBLE
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
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_USER = "User"
        private val ARG_TYPE = "Type"

        fun newInstance(user: User?, type: String): UserManipulationFragment {
            val fragment = UserManipulationFragment()
            val args = Bundle()
            args.putSerializable(ARG_USER, user as java.io.Serializable)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
