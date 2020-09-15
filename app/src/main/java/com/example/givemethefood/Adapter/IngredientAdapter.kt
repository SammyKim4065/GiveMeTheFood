package com.example.givemethefood.Apdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientAdapter(var items: List<String>) : RecyclerView.Adapter<IngredientAdapter.Holder>(){
    class Holder(itemView:View) : RecyclerView.ViewHolder(itemView){
        fun bindindView(item: String) {
            itemView.textView.text = item
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindindView(items[position])
    }
}