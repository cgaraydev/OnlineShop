package com.example.onlineshop.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.onlineshop.R
import com.example.onlineshop.adapter.PicListAdapter
import com.example.onlineshop.adapter.SizeListAdapter
import com.example.onlineshop.databinding.ActivityDetailBinding
import com.example.onlineshop.helper.ManagementCart
import com.example.onlineshop.model.ItemsModel

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)
        getBundle()
        initList()
    }

    private fun initList() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size)
        }
        binding.rvSizeList.adapter = SizeListAdapter(sizeList)
        binding.rvSizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        Glide.with(this)
            .load(colorList[0])
            .into(binding.ivDetail)

        binding.rvDetails.adapter = PicListAdapter(colorList, binding.ivDetail)
        binding.rvDetails.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!
        binding.tvDetailTitle.text = item.title
        binding.tvDetailDescription.text = item.description
        binding.tvDetailPrice.text = item.price.toString()
        binding.tvDetailRating.text = item.rating.toString()
        binding.tvSellerName.text = item.sellerName

        binding.btnAddToCart.setOnClickListener {
            item.numberInCart = numberOrder
            managementCart.insertItems(item)
        }

        binding.btnDetailBack.setOnClickListener {
            finish()
        }
        binding.btnCart.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }

        Glide.with(this)
            .load(item.sellerPicUrl)
            .apply(RequestOptions().transform(CenterCrop()))
            .into(binding.ivDetailSeller)

        binding.ivMessageToSeller.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.setData(Uri.parse("sms:" + item.sellerTell))
            sendIntent.putExtra("sms_body", "type your message")
            startActivity(intent)
        }

        binding.ivCallToSeller.setOnClickListener {
            val phone = item.sellerTell.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
    }
}