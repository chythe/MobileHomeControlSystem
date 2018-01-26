package pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_room.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Room

/**
 *
 */
class RoomsAdapter(roomsList: List<Room>): BaseAdapter() {

    private val rooms = roomsList

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val rowRoom: View

        if (null == convertView) {
            val layoutInflater = LayoutInflater.from(viewGroup!!.context)
            rowRoom = layoutInflater.inflate(R.layout.row_room, viewGroup, false)
            val viewHolder = ViewHolder(rowRoom.row_room_name)
            rowRoom.tag = viewHolder
        } else {
            rowRoom = convertView
        }

        val viewHolder = rowRoom.tag as ViewHolder
        viewHolder.nameTextView.text = rooms[position].name

        return rowRoom
    }

    override fun getItem(position: Int): Any {
        return rooms[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return rooms.size
    }

    private class ViewHolder(val nameTextView: TextView) {
        private var iconType: String = if (nameTextView.text == "Bathroom")
            "ic_simpson"
        else if (nameTextView.text == "Kitchen")
            "ic_play"
        else
            "ic_room"
    }
}