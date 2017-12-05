package pl.polsl.mateusz.chudy.mobileapplication.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_user.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.enums.Role
import pl.polsl.mateusz.chudy.mobileapplication.model.User

/**
 *
 */
class UsersAdapter: BaseAdapter() {

    private val users = arrayListOf<User>(
            User(1, "Mati", "s3r32fszdbfASk", Role.ADMIN),
            User(2, "Ola", "23asdak", Role.USER),
            User(3, "Zbigniew", "2asd32fszdaASk", Role.USER),
            User(4, "User322", "asdsafsdwdbfASk", Role.ADMIN),
            User(5, "Ela", "12323r32fwddbfASk", Role.USER),
            User(6, "Marian", "wwadddSk", Role.GUEST),
            User(7, "Edy", "a2waafszdabdawfASk", Role.USER),
            User(8, "Matt", "23r3dbfASk", Role.GUEST)
    )

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val rowUser: View

        if (null == convertView) {
            val layoutInflater = LayoutInflater.from(viewGroup!!.context)
            rowUser = layoutInflater.inflate(R.layout.row_user, viewGroup, false)
            val viewHolder = ViewHolder(rowUser.row_user_username, rowUser.row_user_role)
            rowUser.tag = viewHolder
        } else {
            rowUser = convertView
        }

        val viewHolder = rowUser.tag as ViewHolder
        viewHolder.usernameTextView.text = users[position].username
        viewHolder.roleTextView.text = users[position].role.name.toLowerCase()

        return rowUser
    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }

    fun remove(position: Int) {
        users.removeAt(position)
    }

    private class ViewHolder(val usernameTextView: TextView, val roleTextView: TextView)
}