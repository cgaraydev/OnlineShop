package com.example.onlineshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.onlineshop.activities.DetailActivity
import com.example.onlineshop.databinding.ViewholderBestSellersBinding
import com.example.onlineshop.model.ItemsModel

class BestSellerAdapter(
    val items: MutableList<ItemsModel>
) : RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder>() {

    inner class BestSellerViewHolder(val binding: ViewholderBestSellersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val binding =
            ViewholderBestSellersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BestSellerViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.binding.tvTitleBestSeller.text = items[position].title
        holder.binding.tvNameBestSeller.text = items[position].price.toString()
        holder.binding.tvRatingBestSeller.text = items[position].rating.toString()

        val requestOption = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOption)
            .into(holder.binding.ivBestSeller)
        holder.itemView.setOnClickListener {
            val intent= Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }
}