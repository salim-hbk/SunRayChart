package com.demo.sunray

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import kotlin.collections.ArrayList


/**
 * Created by Salim.UM on 10-04-2018.
 */
object Util {

    val yyMMdd = SimpleDateFormat("yyyy-MM-dd")

    fun todayYYmmDD() = yyMMdd.format(Date())

    fun todayYYmmDD(input: String) = yyMMdd.parse(input)

    fun getYmD(year: Int, month: Int, dayOfMnth: Int, calendarNew: Calendar?): String {

        calendarNew?.set(Calendar.YEAR, year)
        calendarNew?.set(Calendar.MONTH, month)
        calendarNew?.set(Calendar.DAY_OF_MONTH, dayOfMnth)

        return yyMMdd.format(calendarNew?.time)

    }



    fun generateDemoChrtVals(): ArrayList<Float> {
        val dataSet = ArrayList<Float>()

        for (index in 0 until (24 * 9)) {
            val r = Random()
            val Low = 1
            val High = 7
            var Result = r.nextInt(High - Low) + Low

            if (index == 0)
                Result = 7
            dataSet.add(Result.toFloat())

        }

        return dataSet
    }


}