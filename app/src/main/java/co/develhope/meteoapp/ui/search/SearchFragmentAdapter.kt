package co.develhope.meteoapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.SearchFragmentCardBinding
import co.develhope.meteoapp.networking.domainmodel.Place

class SearchFragmentAdapter(var list: MutableList<Place?>, private val onClick: (Place) -> Unit) :
    RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        val binding =
            SearchFragmentCardBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int) {
        val currentItem = list[position]
        if (currentItem != null) {
            holder.bind(currentItem , onClick)
        }
    }

    class ViewHolder(private val binding: SearchFragmentCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Place , onClick: (Place) -> Unit) {
            "${item.city}, ${item.region}".also { binding.searchedCity.text = it }
            binding.searchCard.setOnClickListener {
                onClick(item)
            }
        }
    }
}