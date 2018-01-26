package pl.polsl.mateusz.chudy.mobileapplication.controllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_switch_type.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.SwitchType

/**
 *
 */
class SwitchTypesAdapter(switchTypesList: List<SwitchType>): BaseAdapter() {

    private val switchTypes = switchTypesList

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val rowSwitchTypes: View

        if (null == convertView) {
            val layoutInflater = LayoutInflater.from(viewGroup!!.context)
            rowSwitchTypes = layoutInflater.inflate(R.layout.row_switch_type, viewGroup, false)
            val viewHolder = ViewHolder(rowSwitchTypes.row_switch_type_name)
            rowSwitchTypes.tag = viewHolder
        } else {
            rowSwitchTypes = convertView
        }

        val viewHolder = rowSwitchTypes.tag as ViewHolder
        viewHolder.nameTextView.text = switchTypes[position].name

        return rowSwitchTypes
    }

    override fun getItem(position: Int): Any {
        return switchTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return switchTypes.size
    }

    private class ViewHolder(val nameTextView: TextView) {
        private var iconType: String = if (nameTextView.text == "Light")
            "ic_switch"
        else if (nameTextView.text == "Door")
            "ic_play"
        else
            "ic_switch2"
    }
}