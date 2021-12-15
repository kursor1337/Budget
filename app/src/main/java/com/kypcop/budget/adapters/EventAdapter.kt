package com.kypcop.budget.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kypcop.budget.R
import com.kypcop.budget.Event

abstract class EventAdapter(context: Context, eventList: List<Event>) :
    ArrayAdapter<Event>(context, R.layout.listview_events, eventList) {

    var onEditClickListener = View.OnClickListener { }
    var onDeleteClickListener = View.OnClickListener { }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event = getItem(position)!!
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.listview_events, LinearLayout(context), false)

        view.setBackgroundResource(
            when (event.type) {
                Event.Type.INCOME -> R.color.transparent_blue
                Event.Type.OUTCOME -> R.color.transparent_red
                else -> R.color.white
            }
        )


        val descriptionTextView = view.findViewById<TextView>(R.id.main)
        val valueTextView = view.findViewById<TextView>(R.id.sec)

        descriptionTextView.text = event.description
        valueTextView.text = when (event.type) {
            Event.Type.INCOME -> "+${event.value}"
            Event.Type.OUTCOME -> "-${event.value}"
            else -> event.value.toString()
        }


        val deleteButton = view.findViewById<ImageButton>(R.id.delete_btn).apply {
            setOnClickListener { onDeleteClick(position) }
        }

        val editButton = view.findViewById<ImageButton>(R.id.edit_btn).apply {
            setOnClickListener { onEditClick(position) }
        }
        return view
    }

    abstract fun onEditClick(position: Int)
    abstract fun onDeleteClick(position: Int)
}


