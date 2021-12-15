package com.kypcop.budget

import android.content.Context


class Month(val monthName: MonthName, val year: Int) {

    var incomeList = ArrayList<Event>()
    var outcomeList = ArrayList<Event>()

    fun addEvent(event: Event) {
        if (event.type == Event.Type.INCOME) {
            incomeList.add(event)
        } else {
            outcomeList.add(event)
        }
        Tools.updateMonthList()
    }

    fun editEvent(index: Int, event: Event) {
        if (event.type == Event.Type.INCOME) {
            incomeList[index] = event
        } else {
            outcomeList[index] = event
        }
        Tools.updateMonthList()
    }

    fun deleteEvent(event: Event) {
        if (event.type == Event.Type.INCOME) {
            incomeList.remove(event)
        } else {
            outcomeList.remove(event)
        }
        Tools.updateMonthList()
    }

    fun fullEnName(): String {
        return "${monthName.getEnName()} $year"
    }

    fun fullRuName(): String {
        return "${monthName.getRuName()} $year"
    }

    fun fullLocalizedName(context: Context): String {
        return "${monthName.getLocalizedName(context)} $year"
    }

    fun totalIncome(): Int {
        return incomeList.sumOf { it.value }
    }

    fun totalOutcome(): Int {
        return outcomeList.sumOf { it.value }
    }

    fun balance(): Int {
        return totalIncome() - totalOutcome()

    }

    companion object {
        const val INDEX = "index"
    }


}