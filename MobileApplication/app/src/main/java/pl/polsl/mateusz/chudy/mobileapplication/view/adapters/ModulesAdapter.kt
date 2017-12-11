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
class ModulesAdapter(modulesList: List<Module>): BaseAdapter() {

    private val modules = modulesList

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
        if (modules[position].name.isEmpty()) {
            viewHolder.nameTextView.text = modules[position].ipAddress
        } else {
            viewHolder.nameTextView.text = modules[position].name
        }

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

    private class ViewHolder(val nameTextView: TextView)
}