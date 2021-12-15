package com.kypcop.budget

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Tools {

    const val PREF = "pref"
    const val MONTH_LIST = "month_list"
    const val PLANNED_EXPENSES = "planned_expenses"

    lateinit var pref: SharedPreferences
    var monthList = ArrayList<Month>()
    var plannedExpenses = ArrayList<Event>()
    val gson = Gson()

    fun initPref(context: Context) {
        pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        val jsonMonthList = pref.getString(MONTH_LIST, "")
        if (jsonMonthList != "") {
            monthList = gson.fromJson(jsonMonthList, object : TypeToken<ArrayList<Month>>() {}.type)
        }

        val sdf = SimpleDateFormat("MMM yyyy", Locale.US)
        val date = sdf.format(Date())
        val year = date.substring(4).toInt()
        val month = date.substring(0, 3)
        if (date !in monthList.map { it.fullEnName() }) {
            monthList.add(Month(MonthName.getInstanceByName(month), year))
            updateMonthList()
        }
        val jsonPlannedExpenses = pref.getString(PLANNED_EXPENSES, "")
        if (jsonPlannedExpenses != "") {
            plannedExpenses = gson.fromJson(
                jsonPlannedExpenses,
                object : TypeToken<ArrayList<Event>>() {}.type
            )
        }
    }

    fun addPlannedExpense(event: Event) {
        plannedExpenses.add(event)
        updatePlannedExpenses()
    }

    fun editPlannedExpense(index: Int, event: Event) {
        plannedExpenses[index] = event
        updatePlannedExpenses()
    }

    fun deletePlannedExpense(index: Int) {
        plannedExpenses.removeAt(index)
        updatePlannedExpenses()
    }

    fun updatePlannedExpenses() {
        val editor = pref.edit()
        editor.putString(PLANNED_EXPENSES, gson.toJson(plannedExpenses))
        editor.apply()
    }


    fun updateMonthList() {
        val editor = pref.edit()
        editor.putString(MONTH_LIST, gson.toJson(monthList))
        editor.apply()
    }
}