package pl.polsl.mateusz.chudy.mobileapplication.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_module.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.Module

/**
 *
 */
class ModulesAdapter: BaseAdapter() {

    private val modules = arrayListOf<Module>(
            Module(1, "Bathroom lights next to the door"),
            Module(2, "Bathroom contact"),
            Module(3, "Bedroom lights"),
            Module(4, "Kitchen lights 1"),
            Module(5, "Kitchen lights 2"),
            Module(6, "Dining Room Blinds")
    )

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val rowModule: View

        if (null == convertView) {
            val layoutInflater = LayoutInflater.from(viewGroup!!.context)
            rowModule = layoutInflater.inflate(R.layout.row_module, viewGroup, false)
            val viewHolder = ViewHolder(rowModule.row_module_name)
            rowModule.tag = viewHolder
        } else {
            rowModule = convertView
        }

        val viewHolder = rowModule.tag as ViewHolder
        viewHolder.nameTextView.text = modules[position].name

        return rowModule
    }

    override fun getItem(position: Int): Any {
        return modules[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return modules.size
    }

    fun remove(position: Int) {
        modules.removeAt(position)
    }

    private class ViewHolder(val nameTextView: TextView)
}