package co.develhope.meteoapp.ui.domani

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.databinding.TomorrowScreenRowCardBinding
import co.develhope.meteoapp.databinding.TomorrowScreenTitleBinding

class AdapterTomorrowScreen (

    var items: List<TomorrowCardInfo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val forecast_tomorrow_screen = 0
    private val title_tomorrow_screen = 1

    class Tomorrow_Row_Card_ViewHolder(private val rowCardBinding: TomorrowScreenRowCardBinding)
        : RecyclerView.ViewHolder(rowCardBinding.root)

    class Tomorrow_Title_ViewHolder(private val titleBinding: TomorrowScreenTitleBinding)
        : RecyclerView.ViewHolder(titleBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){

            forecast_tomorrow_screen ->
                Tomorrow_Row_Card_ViewHolder(TomorrowScreenRowCardBinding.inflate
                    (LayoutInflater.from(parent.context), parent, false))

            title_tomorrow_screen ->
                Tomorrow_Title_ViewHolder(TomorrowScreenTitleBinding.inflate
                    (LayoutInflater.from(parent.context), parent, false))

            else -> throw java.lang.IllegalArgumentException("ERROR")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }
}