package com.mypharmacybd.ui.main_activity.fragments.categories.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypharmacybd.R
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract

class OrdersAdapter(
    private val view:OrderListContract.View,
    private val context: Context,
    private val ordersResponse: GetOrderResponse
    ) : RecyclerView.Adapter<OrdersAdapter
.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_orders, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = ordersResponse.data[position]
        Log.e("Dim"," orderData.orderNumber :"+ orderData.orderNumber)
        holder.tvCategoryName.text = "Order id ${orderData.orderNumber}"
       // holder.tvCategoryName.setOnClickListener { view.onCategoryClicked(category)}
    }

    override fun getItemCount(): Int  = ordersResponse.data.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategoryName:TextView = itemView.findViewById(R.id.tvOrderNumber)
    }
}