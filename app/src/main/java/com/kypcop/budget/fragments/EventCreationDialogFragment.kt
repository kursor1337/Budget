package com.kypcop.budget.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.kypcop.budget.R
import com.kypcop.budget.Event
import com.kypcop.budget.Month
import com.kypcop.budget.Tools

class EventCreationDialogFragment : DialogFragment() {

    var eventType = Event.Type.PLANNED
    var reason = CREATE
    var monthIndex = -1
    var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventType = it.getSerializable(EVENT_TYPE_KEY) as Event.Type
            reason = it.getString(REASON) ?: CREATE
            if (reason == EDIT) index = it.getInt(Event.INDEX)
            if (eventType != Event.Type.PLANNED) monthIndex = it.getInt(Month.INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_creation, LinearLayout(activity), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val valueEditText = view.findViewById<EditText>(R.id.value_et)
        val descriptionEditText = view.findViewById<EditText>(R.id.description_et)
        val eventTypeTextView = view.findViewById<TextView>(R.id.event_type_tv)
        eventTypeTextView.text = eventType.name
        if (reason == EDIT) {
            val description = when (eventType) {
                Event.Type.PLANNED -> Tools.plannedExpenses[index].description
                Event.Type.INCOME -> Tools.monthList[monthIndex].incomeList[index].description
                else -> Tools.monthList[monthIndex].outcomeList[index].description
            }
            val value = when (eventType) {
                Event.Type.PLANNED -> Tools.plannedExpenses[index].value
                Event.Type.INCOME -> Tools.monthList[monthIndex].incomeList[index].value
                else -> Tools.monthList[monthIndex].outcomeList[index].value
            }
            descriptionEditText.setText(description)
            valueEditText.setText(value.toString())
        }
        val readyButton = view.findViewById<Button>(R.id.ready_btn).also {
            it.setOnClickListener {
                if (valueEditText.text.isNotEmpty() && descriptionEditText.text.isNotEmpty()) {

                    val description = descriptionEditText.text.toString()
                    val value = valueEditText.text.toString().toInt()
                    val event = Event(description, value, eventType)

                    val previousFragment =
                        parentFragmentManager.fragments[parentFragmentManager.fragments.size - 2]
                    if (reason == EDIT) {
                        when (eventType) {
                            Event.Type.PLANNED -> {
                                Tools.editPlannedExpense(index, event)
                                (previousFragment as PlannedExpFragment).updateDisplayingData()
                            }
                            else -> {
                                Tools.monthList[monthIndex].editEvent(index, event)
                                (previousFragment as BudgetFragment).updateDisplayingData()
                            }
                        }
                    } else {
                        when (eventType) {
                            Event.Type.PLANNED -> {
                                Tools.addPlannedExpense(event)
                                (previousFragment as PlannedExpFragment).updateDisplayingData()
                            }
                            else -> {
                                Tools.monthList[monthIndex].addEvent(event)
                                (previousFragment as BudgetFragment).updateDisplayingData()
                            }
                        }
                    }
                    dismiss()

                } else {
                    Toast.makeText(activity, "Fill the gaps", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }


    companion object {
        private const val TYPE = "type"
        const val EVENT_TYPE_KEY = "event type key"
        const val EVENT_KEY = "event key"
        const val EDIT = "edit"
        const val CREATE = "create"
        const val REASON = "reason"

    }
}
