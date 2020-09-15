package com.example.givemethefood.Apdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.example.givemethefood.viewModel.FoodViewModel
import kotlinx.android.synthetic.main.item_order_delivery.view.*

class DeliveryOrderAdapter(var item:List<FoodsItem>,var foodViewModel: FoodViewModel,var lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<DeliveryOrderAdapter.Holder>() {
  inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: FoodsItem){
            itemView.apply {
                foodname_text.text = item.foodname
                piece_text.text = item.piece.toString()
                val restaurant = foodViewModel.getRestaurantItemById(item.resIdFk!!)
                restaurant.observe(lifecycleOwner, Observer {
                    resname_textview.text = it.resname
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_delivery,parent,false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(item[position])
    }
}