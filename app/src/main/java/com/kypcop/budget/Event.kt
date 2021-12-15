package com.kypcop.budget

import java.io.Serializable

class Event(val description: String, val value: Int, val type: Type) : Serializable {

    enum class Type {
        INCOME, OUTCOME, PLANNED
    }

    override fun toString(): String {
        return "$description; $value; $type"
    }

    companion object {
        const val TYPE = "type"
        const val VALUE = "value"
        const val DESCRIPTION = "description"
        const val INDEX = "index"
    }

}
