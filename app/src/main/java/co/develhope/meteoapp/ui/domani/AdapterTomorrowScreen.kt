package co.develhope.meteoapp.ui.domani

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.TomorrowScreenRowCardBinding

class AdapterTomorrowScreen (

    var items: List<TomorrowCardInfo>
) : RecyclerView.Adapter<AdapterTomorrowScreen.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: TomorrowScreenRowCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TomorrowScreenRowCardBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            tomorrowHourTV.text = items[position].date
            tomorrowTempTV.text = items[position].temperature.toString()
            tomorrowPrecipitationTV.text = items[position].precipitation.toString()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}