package com.example.givemethefood.Apdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.givemethefood.Data.RestaurantItem
import com.example.givemethefood.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantFilterAdapter(var item: List<RestaurantItem>, private val clickListener: ClickListener)
    : RecyclerView.Adapter<RestaurantFilterAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: RestaurantItem) {
            itemView.apply {
                resName.text = item.resname
                Picasso.get().load(item.resPicture).fit().centerCrop().noPlaceholder().into(resImage)
            }
        }
    }

    interface ClickListener {
        fun onResFilterClickListener(restaurantItem: RestaurantItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(item[position])
        holder.itemView.setOnClickListener {
            clickListener.onResFilterClickListener(item[position])
        }
    }
}
