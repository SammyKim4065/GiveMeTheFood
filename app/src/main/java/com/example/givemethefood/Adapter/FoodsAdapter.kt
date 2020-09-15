package com.example.givemethefood.Apdater

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.example.givemethefood.viewModel.FoodViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.MaskTransformation
import kotlinx.android.synthetic.main.item_food.view.*
import java.util.*
import kotlin.collections.ArrayList


class FoodsAdapter(
    var foodlist: List<FoodsItem>,
    var foodViewModel: FoodViewModel,
    var lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<FoodsAdapter.Holder>(), Filterable {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(item: FoodsItem) {
            itemView.apply {
                food_name_textView.text = item.foodname
                price_textView.text = "Price ${item.foodprice} ฿"
                val restaurant = foodViewModel.getRestaurantItemById(item.resIdFk!!)
                restaurant.observe(lifecycleOwner, Observer {
                    res_name_textview.text = it.resname
                })
                number_textview.text = item.piece.toString()
                ingredient_recyclerView.apply {
                    layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                    adapter = IngredientAdapter(item.ingredient!!)
                }
                when (item.piece != 0) {
                    true -> expand(itemView)
                    else -> collapse(itemView)
                }
                when (item.isfev) {
                    true -> itemView.fev_imageview.setImageResource(R.drawable.redheart)
                    else -> itemView.fev_imageview.setImageResource(R.drawable.grayheart)
                }
            }
            Picasso
                .get()
                .load(item.foodpic)
                .transform(MaskTransformation(itemView.context, R.drawable.food_frame))
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(itemView.food_imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_food, parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return foodlist.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val foodPosition = foodlist[position]
        val totalPiece = foodViewModel.totalPiece.value

        holder.bindView(foodlist[position])

        holder.itemView.setOnClickListener {
            when (it.expandview.isExpanded) {
                true -> collapse(it)
                else -> expand(it)
            }
        }

        holder.itemView.add_image.setOnClickListener {
            foodViewModel.totalPiece.value = totalPiece?.plus(1)
            foodPosition.piece += 1
            foodViewModel.addPiece(foodPosition.foodId!!, foodPosition)
            notifyItemChanged(position)
        }
        holder.itemView.remove_imageView.setOnClickListener {
            if (totalPiece != 0) {
                foodPosition.piece -= 1
                foodViewModel.totalPiece.value = totalPiece?.minus(1)
                foodViewModel.minusPiece(foodPosition.foodId!!, foodPosition)
                notifyItemChanged(position)
            }
        }

        holder.itemView.fev_imageview.setOnClickListener {
            when (foodPosition.isfev) {
                true -> {
                    foodPosition.isfev = false
                    foodViewModel.updateFavourite(foodPosition.foodId!!, foodPosition)
                }
                else -> {
                    foodPosition.isfev = true
                    foodViewModel.updateFavourite(foodPosition.foodId!!, foodPosition)
                }
            }
            notifyItemChanged(position)
            Log.i(
                "fev", "${foodPosition.foodId} + " +
                        "${foodPosition.foodname} + " +
                        "${foodPosition.isfev}"
            )
        }

    }

    private fun expand(v: View) {
        v.expandview.expand()
    }

    private fun collapse(v: View) {
        v.expandview.collapse()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val resultList = ArrayList<FoodsItem>()
                for (row in foodlist) if (row.foodname?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) resultList.add(row)
                foodlist = resultList

                val filterResults = FilterResults()
                filterResults.values = foodlist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                foodlist = results?.values as List<FoodsItem>
                notifyDataSetChanged()
            }

        }
    }


}
