package com.kypcop.budget.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.kypcop.budget.Month
import com.kypcop.budget.R
import kotlin.math.sign

class MonthsAdapter(context: Context, private val mMonthList: List<Month>) :
    ArrayAdapter<Month>(context, R.layout.listview_months, mMonthList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.listview_months, LinearLayout(context), false)
        val monthNameTextView = view.findViewById<TextView>(R.id.main)
        val balanceTextView = view.findViewById<TextView>(R.id.sec)

        val month = mMonthList[position]

        monthNameTextView.text = month.fullLocalizedName(context)
        when {
            month.balance() > 0 -> {
                balanceTextView.setTextColor(context.getColor(R.color.transparent_blue))
                balanceTextView.text = "+${month.balance()}"
            }
            month.balance() < 0 -> {
                balanceTextView.setTextColor(context.getColor(R.color.transparent_red))
                balanceTextView.text = month.balance().toString()
            }
            else -> {
                balanceTextView.text = month.balance().toString()
            }
        }

        return view

    }
}