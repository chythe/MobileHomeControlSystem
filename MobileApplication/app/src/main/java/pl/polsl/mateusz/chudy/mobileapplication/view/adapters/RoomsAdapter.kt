package pl.polsl.mateusz.chudy.mobileapplication.view.adapters

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
class RoomsAdapter: BaseAdapter() {

    private val rooms = arrayListOf<Room>(
            Room(1, "Bathroom"),
            Room(2, "Living Room"),
            Room(3, "Bedroom"),
            Room(4, "Kitchen"),
            Room(5, "Wardrobe"),
            Room(6, "Garage"),
            Room(7, "Anteroom"),
            Room(8, "Toilet"),
            Room(9, "Attic"),
            Room(10, "Cellar"),
            Room(11, "Cubby"),
            Room(12, "Dining Room")
    )

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

    fun remove(position: Int) {
        rooms.removeAt(position)
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