package co.develhope.meteoapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.SearchFragmentCardBinding
import co.develhope.meteoapp.networking.domainmodel.Place

class SearchFragmentAdapter(private val list: List<Place>,private val onClick: (Place) -> Unit) :
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
        holder.bind(currentItem, onClick)
    }

    class ViewHolder(private val binding: SearchFragmentCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Place, onClick: (Place) -> Unit) {
            binding.searchedCity.text = item.city
            binding.weatherCondition.text = item.weather
            "${item.temperature}°".also { binding.cityTemperature.text = it }
            binding.searchCard.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("city",item.city)
                }
                itemView.findNavController().navigate(R.id.navigation_home,bundle)
            }
        }
    }
}