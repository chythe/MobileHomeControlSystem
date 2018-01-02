package pl.polsl.mateusz.chudy.mobileapplication.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.row_module_configuration.view.*
import pl.polsl.mateusz.chudy.mobileapplication.R
import pl.polsl.mateusz.chudy.mobileapplication.model.ModuleConfiguration

/**
 *
 */
class ModuleConfigurationsAdapter(moduleConfigurationsList: List<ModuleConfiguration>): BaseAdapter() {

    private val moduleConfigurations = moduleConfigurationsList

    fun getModuleConfigurations(): List<ModuleConfiguration> {
        return moduleConfigurations
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val rowModule: View

        if (null == convertView) {
            val layoutInflater = LayoutInflater.from(viewGroup!!.context)
            rowModule = layoutInflater.inflate(R.layout.row_module_configuration, viewGroup, false)
            val viewHolder = ViewHolder(rowModule.row_module_configuration_icon_configuration, rowModule.row_module_configuration_name)
            rowModule.tag = viewHolder
        } else {
            rowModule = convertView
        }

        val viewHolder = rowModule.tag as ViewHolder
        viewHolder.nameTextView.text = moduleConfigurations[position].name
        if (moduleConfigurations[position].state)
            viewHolder.iconImageView.setColorFilter(rowModule.resources.getColor(R.color.colorGreen))
        else
            viewHolder.iconImageView.setColorFilter(rowModule.resources.getColor(R.color.colorPrimary))

        return rowModule
    }

    override fun getItem(position: Int): Any {
        return moduleConfigurations[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return moduleConfigurations.size
    }

    private class ViewHolder(val iconImageView: ImageView, val nameTextView: TextView)
}