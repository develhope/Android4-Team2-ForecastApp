package co.develhope.meteoapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.SearchFragmentCardBinding
import co.develhope.meteoapp.networking.domainmodel.Place

class SearchFragmentAdapter(private val list: List<Place>) :
    RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchFragmentCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    class ViewHolder(private val binding: SearchFragmentCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Place) {
            binding.searchedCity.text = item.city
            binding.weatherCondition.text = item.weather
            "${item.temperature}°".also { binding.cityTemperature.text = it }
        }
    }
}