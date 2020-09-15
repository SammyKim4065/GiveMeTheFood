package com.example.givemethefood.Apdater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.givemethefood.Data.RestaurantItem
import com.example.givemethefood.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantAdapter(var items: List<RestaurantItem>) : RecyclerView.Adapter<RestaurantAdapter.Holder>() {

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(item: RestaurantItem){
            itemView.apply {
                resName.text = item.resname
                Picasso.get().load(item.resPicture).fit().centerCrop().into(resImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from((parent.context)).inflate(R.layout.item_restaurant,
            parent,false))
    }

    override fun getItemCount(): Int {
      return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        items[position].let { holder.bind(it) }
    }
}