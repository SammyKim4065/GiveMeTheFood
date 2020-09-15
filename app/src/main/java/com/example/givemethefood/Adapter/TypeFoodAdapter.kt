package com.example.givemethefood.Apdater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.example.givemethefood.Data.FoodType
import com.example.givemethefood.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.MaskTransformation
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.item_restaurant.view.*
import kotlinx.android.synthetic.main.item_typeoffood.view.*

class TypeFoodAdapter(var item: List<FoodType>) : RecyclerView.Adapter<TypeFoodAdapter.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: FoodType) {
            itemView.typefoodName.text = item.name
            Picasso
                .get()
                .load(item.picture)
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(itemView.typefoodimage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_typeoffood, parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(item[position])
    }
}