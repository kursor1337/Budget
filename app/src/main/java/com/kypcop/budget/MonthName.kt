package com.kypcop.budget

import android.content.Context
import java.security.spec.ECParameterSpec
import java.util.*

enum class MonthName {

    JANUARY {
        override fun getRuName() = "Янв"
        override fun getLocalizedName(context: Context) = context.getString(R.string.jan_name)
    },
    FEBRUARY {
        override fun getRuName() = "Фев"
        override fun getLocalizedName(context: Context) = context.getString(R.string.feb_name)
    },
    MARCH {
        override fun getRuName() = "Мар"
        override fun getLocalizedName(context: Context) = context.getString(R.string.mar_name)
    },
    APRIL {
        override fun getRuName() = "Апр"
        override fun getLocalizedName(context: Context) = context.getString(R.string.apr_name)
    },
    MAY {
        override fun getRuName() = "Май"
        override fun getLocalizedName(context: Context) = context.getString(R.string.may_name)
    },
    JUNE {
        override fun getRuName() = "Июн"
        override fun getLocalizedName(context: Context) = context.getString(R.string.jun_name)
    },
    JULY {
        override fun getRuName() = "Июл"
        override fun getLocalizedName(context: Context) = context.getString(R.string.jul_name)
    },
    AUGUST {
        override fun getRuName() = "Авг"
        override fun getLocalizedName(context: Context) = context.getString(R.string.aug_name)
    },
    SEPTEMBER {
        override fun getRuName() = "Сен"
        override fun getLocalizedName(context: Context) = context.getString(R.string.sep_name)
    },
    OCTOBER {
        override fun getRuName() = "Окт"
        override fun getLocalizedName(context: Context) = context.getString(R.string.oct_name)
    },
    NOVEMBER {
        override fun getRuName() = "Ноя"
        override fun getLocalizedName(context: Context) = context.getString(R.string.nov_name)
    },
    DECEMBER {
        override fun getRuName() = "Дек"
        override fun getLocalizedName(context: Context) = context.getString(R.string.dec_name)
    };

    companion object {
        fun getInstanceByName(string: String): MonthName {
            return when (string) {
                "Jan" -> JANUARY
                "Feb" -> FEBRUARY
                "Mar" -> MARCH
                "Apr" -> APRIL
                "May" -> MAY
                "Jun" -> JUNE
                "Jul" -> JULY
                "Aug" -> AUGUST
                "Sep" -> SEPTEMBER
                "Oct" -> OCTOBER
                "Nov" -> NOVEMBER
                "Dec" -> DECEMBER
                "Янв" -> JANUARY
                "Фев" -> FEBRUARY
                "Мар" -> MARCH
                "Апр" -> APRIL
                "Май" -> MAY
                "Июн" -> JUNE
                "Июл " -> JULY
                "Авг" -> AUGUST
                "Сен" -> SEPTEMBER
                "Окт" -> OCTOBER
                "Ноя" -> NOVEMBER
                "Дек" -> DECEMBER
                else -> return JANUARY
            }
        }

    }

    abstract fun getLocalizedName(context: Context): String
    abstract fun getRuName(): String

    fun getEnName(): String {
        return this.toString().substring(0, 3).toLowerCase(Locale.US).capitalize(Locale.US)
    }

}