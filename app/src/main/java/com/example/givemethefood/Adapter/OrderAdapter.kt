package com.example.givemethefood.Apdater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.example.givemethefood.viewModel.FoodViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_order.view.*


class OrderAdapter(var foodlist:List<FoodsItem>,
val foodViewModel:FoodViewModel,
val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<OrderAdapter.Holder>() {
   inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(item: FoodsItem){
            itemView.apply {
                Picasso.get().load(item.foodpic).centerCrop().fit().into(itemView.order_food_image)
                order_food_name.text = item.foodname
                val restaurant = foodViewModel.getRestaurantItemById(item.resIdFk!!)
                restaurant.observe(lifecycleOwner, androidx.lifecycle.Observer {
                    order_res_name.text = it.resname.toString()
                })
                order_peice.text =  "Price ${item.foodprice} X ${item.piece}"
                order_total_price.text = (item.foodprice!!.toInt() * item.piece).toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_order,parent,false))
    }

    override fun getItemCount(): Int {
        return foodlist.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(foodlist[position])
    }


}