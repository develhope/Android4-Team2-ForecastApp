package co.develhope.meteoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.ItemTomScreenBinding


class AdapterTomorrowScreen(
    var items: List<ItemTomorrowScreen>
) : RecyclerView.Adapter<AdapterTomorrowScreen.ItemViewHolder>(){

    inner class ItemViewHolder(val binding: ItemTomScreenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTomScreenBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }
}