package pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_user.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.User

/**
 *
 */
class UsersAdapter(usersList: List<User>): BaseAdapter() {

    private val users = usersList

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

    private class ViewHolder(val usernameTextView: TextView, val roleTextView: TextView)
}