package com.example.onlineshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.adapter.CartAdapter
import com.example.onlineshop.databinding.ActivityCartBinding
import com.example.onlineshop.helper.ChangeNumberItemsListener
import com.example.onlineshop.helper.ManagementCart

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        setVariable()
        initCartList()
        calculateCart()
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 15.0
        tax = Math.round(managementCart.getTotalFee() * percentTax)*100/100.0
        val total = Math.round(managementCart.getTotalFee() + tax + delivery)*100/100.0
        val itemTotal = Math.round(managementCart.getTotalFee()*100)/100.0

        with(binding){
            tvSubtotalValue.text = "$$itemTotal"
            tvDeliveryValue.text = "$$delivery"
            tvTaxCartValue.text = "$$tax"
            tvTotalCartValue.text = "$$total"
        }
    }

    private fun initCartList() {
        binding.rvCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCart.adapter = CartAdapter(managementCart.getListCart(),this,object :ChangeNumberItemsListener{
            override fun onChanged() {
                calculateCart()
            }
        })
    }

    private fun setVariable() {
        binding.ivBackCart.setOnClickListener {
            finish()
        }

    }
}