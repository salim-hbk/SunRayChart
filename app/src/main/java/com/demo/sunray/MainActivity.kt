package com.demo.sunray

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.sunray.SunRayChart
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sunRayChart = SunRayChart(this)
        sunRayChart.addEntries(resources.getTextArray(R.array.datset_labels))
        sunRayChart.setFonSize(resources.getDimension(R.dimen.nine_sp))
        sunRayChart.setFontColor(ContextCompat.getColor(this, R.color.gray_light))
        sunRayChart.addDataSet(Util.generateDemoChrtVals())
        sunRayChart.invalidate()
        lVcontainer.addView(sunRayChart)
    }
}
