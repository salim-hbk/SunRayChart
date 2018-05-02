package com.sunray

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import java.util.*


/**
 * Created by Salim.UM on 06-04-2018.
 */
class SunRayChart(context: Context) : View(context) {

    constructor(context: Context, attrs: AttributeSet) : this(context) {
        parseDataFrmAttrs(attrs)
    }

    private var circleWIdth: Float = 0f
    private var circleHight: Float = 0f
    private var circleRadius: Float = 0f
    private var circleRadiusSmall: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var imgCircle: Bitmap? = null
    private val CIR_MARGIN = 120
    private var cannotPlotMap = false
    private val paintBlack = Paint()
    private val paintGray = Paint()
    private val paintGrayLight = Paint()
    private val paintGrdient = Paint()
    private val paintText = Paint()
    private val rect = Rect()
    private val dataSetLabels = ArrayList<Int>()
    private val dataSetOreginal = ArrayList<Float>()
    private val dataSet = ArrayList<Int>()

    private var textSize: Float = 10f
    private var textColor: Int = 0
    private var MAX_CHRT_VALUE = (24 * 9)
    private var MAX_BALLS_ROW = 48

    companion object {
        public val MAX_RANGE = 7 // 6 = INDEX STARTS FROM 0
    }


    init {

        imgCircle = BitmapFactory.decodeResource(resources, R.drawable.circle_shdw)
        createPaint()
        setOnMeasureClBck()
    }

    private fun parseDataFrmAttrs(attrs: AttributeSet) {
        val attributeSet = context.theme.obtainStyledAttributes(attrs, R.styleable.SunRayChart, 0, 0)
        getDataSet(attributeSet)
        getFontAttr(attributeSet)

    }

    fun addEntries(textArray: Array<CharSequence>) {
        if (textArray != null) {
            dataSetLabels.clear()
            for (index in 0 until textArray.size) {
                dataSetLabels.add(textArray[index].toString().toInt())
            }
            MAX_CHRT_VALUE = ((dataSetLabels.size * 2) * 9)
            MAX_BALLS_ROW = dataSetLabels.size * 4

        }
    }
    fun setFonSize(dimension: Float) {
        var defFntSize = getDpFrmPx(context, 10f)
        if(dimension<1){
            defFntSize=dimension
        }
        textSize = defFntSize
    }
    fun setFontColor(color: Int) {
        textColor = color
        paintText.color = textColor
        paintText.style = Paint.Style.FILL
        paintText.textSize = textSize
    }

    private fun getFontAttr(attributeSet: TypedArray?) {
        val fontClr = attributeSet?.getString(R.styleable.SunRayChart_fontColor)

        val defFntSize = getDpFrmPx(context, 10f).toInt()
        val fontSize = attributeSet?.getDimensionPixelSize(R.styleable.SunRayChart_fontSize,
                defFntSize)

        textSize = fontSize?.toFloat() ?: defFntSize.toFloat()
        textColor = Color.parseColor(fontClr)

        paintText.color = textColor
        paintText.style = Paint.Style.FILL
        paintText.textSize = textSize


    }

    private fun getDataSet(attributeSet: TypedArray?) {
        val dataSetAttr = attributeSet?.getTextArray(R.styleable.SunRayChart_android_entries)
        if (dataSetAttr != null) {
            dataSetLabels.clear()

            for (index in 0 until dataSetAttr.size) {
                dataSetLabels.add(dataSetAttr[index].toString().toInt())
            }

            MAX_CHRT_VALUE = ((dataSetLabels.size * 2) * 9)
            MAX_BALLS_ROW = dataSetLabels.size * 4

        }
    }

    private fun createPaint() {

        paintBlack.color = Color.parseColor("#000000")
        paintBlack.style = Paint.Style.STROKE
        paintBlack.strokeWidth = 2f

        paintGray.color = Color.GRAY
        paintGray.style = Paint.Style.FILL
        paintGray.textSize = getDpFrmPx(context, 10f)

        paintGrayLight.color = Color.parseColor("#eeeeee")
        paintGrayLight.style = Paint.Style.FILL
        paintGrayLight.textSize = getDpFrmPx(context, 10f)

    }

    private var canvas: Canvas? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
      //  drawBgShadow(canvas)
        //drawGuideLines(canvas)
        drawNumerals(canvas)
        drawNumLines(canvas)
        chartBgBlls(canvas)
        if (dataSet.size > 0)
            drawChart(canvas)

    }

    private fun drawBgShadow(canvas: Canvas?) {

        canvas?.drawBitmap(imgCircle, CIR_MARGIN.toFloat() + 25,
                CIR_MARGIN.toFloat() + 85, null)

    }

    private fun chartBgBlls(canvas: Canvas?) {

        for (index in 0 until MAX_CHRT_VALUE) {

            for (i in 1 until MAX_RANGE) {

                val numberDigit = index
                val angle = Math.PI / MAX_BALLS_ROW * (numberDigit - 6)

                val radius = i + 1 * 8
                val whiteSpace = (radius + radius + 5)

                val xCoordinate = centerX + Math.cos(angle) * (circleRadiusSmall + i * whiteSpace)
                val yCoordinate = centerY + Math.sin(angle) * (circleRadiusSmall + i * whiteSpace)

                canvas?.drawCircle(xCoordinate.toFloat(), yCoordinate.toFloat(), radius.toFloat(), paintGrayLight)

            }

        }


    }

    private fun drawChart(canvas: Canvas?) {

        for (index in 0 until MAX_CHRT_VALUE) {
            var Result = 0
            if (dataSet.size > index) {
                Result = dataSet[index]
            }

            for (i in 1 until Result) {

                val numberDigit = index
                val angle = Math.PI / MAX_BALLS_ROW * (numberDigit - 24)

                val radius = i + 1 * 8
                val whiteSpace = (radius + radius + 5)


                val xCoordinate = centerX + Math.cos(angle) * (circleRadiusSmall + i * whiteSpace)
                val yCoordinate = centerY + Math.sin(angle) * (circleRadiusSmall + i * whiteSpace)

                paintGrdient.color = Color.rgb(3, 190 + (i * -10), 188 + (i * 5))
                paintGrdient.style = Paint.Style.FILL

                canvas?.drawCircle(xCoordinate.toFloat(), yCoordinate.toFloat(), radius.toFloat(), paintGrdient)


            }

        }

        invalidate()
    }

    private fun drawNumLines(canvas: Canvas?) {

        for (index in 0 until 25) {
            val numberDigit = index
            val angle = Math.PI / 12 * (numberDigit - 6)
            val xCoordinate = centerX + Math.cos(angle) * (circleRadiusSmall - 10)
            val yCoordinate = centerY + Math.sin(angle) * (circleRadiusSmall - 10)
            canvas?.drawCircle(xCoordinate.toFloat(), yCoordinate.toFloat(), 5f, paintText)
        }

    }

    fun addDataSet(dataSetNew: ArrayList<Float>) {
        Toast.makeText(context, "called", Toast.LENGTH_SHORT).show()
        if (dataSetNew == null || dataSetNew.size == 0) {
            cannotPlotMap = true
            return
        }
        dataSetOreginal.addAll(dataSetNew)
        this.dataSet.addAll(converInpDatasetToRange(dataSetNew))

    }


    private fun drawNumerals(canvas: Canvas?) {

        for (index in 0 until dataSetLabels.size) {
            val numberDigit = dataSetLabels[index]
            val numbrTxt = numberDigit.toString()
            paintGray.getTextBounds(numbrTxt, 0, numbrTxt.length, rect)

            val angle = Math.PI / 12 * (numberDigit - 6)

            val xCoordinate = centerX + Math.cos(angle) * (circleRadiusSmall - 60) - rect.width() / 2
            val yCoordinate = centerY + Math.sin(angle) * (circleRadiusSmall - 60) + rect.height() / 2

            canvas?.drawText(numbrTxt, xCoordinate.toFloat(), yCoordinate.toFloat(), paintText)

        }

    }

    private fun drawGuideLines(canvas: Canvas?) {

        canvas?.drawCircle(centerX, centerY, circleRadius, paintBlack)
        canvas?.drawCircle(centerX, centerY, circleRadiusSmall, paintBlack)

    }


    private fun setOnMeasureClBck() {
        val vto = viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                removeOnGlobalLayoutListener(this)
                circleWIdth = measuredWidth.toFloat() - getDpFrmPx(context, CIR_MARGIN.toFloat())
                circleHight = measuredHeight.toFloat() - getDpFrmPx(context, CIR_MARGIN.toFloat())
                circleRadius = circleWIdth / 2

                circleRadiusSmall = circleRadius / 2

                centerX = measuredWidth.toFloat() / 2
                centerY = measuredHeight.toFloat() / 2


                imgCircle = getScaledImg(imgCircle!!,
                        circleWIdth.toInt() + getDpFrmPx(context, 60f).toInt(),
                        circleWIdth.toInt() + getDpFrmPx(context, 60f).toInt())


            }
        })

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun removeOnGlobalLayoutListener(onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener) {

        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener)
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener)
        }
    }

    fun getDpFrmPx(context: Context, dimen: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dimen,
                    context.getResources().getDisplayMetrics())

    fun converInpDatasetToRange(dataSetOreginal: ArrayList<Float>): ArrayList<Int> {
        val max = dataSetOreginal.max()
        val min = dataSetOreginal.min()
        val dataSet = ArrayList<Int>()

        dataSetOreginal.forEach { input ->
            val range = (input - min!!) / (max!! - min!!) * SunRayChart.MAX_RANGE
            dataSet.add(range.toInt())
            Log.d("Canvas", "value range =>$range")
        }
        return dataSet
    }

    fun getScaledImg(imgCircle: Bitmap, circleWIdth: Int, circleHight: Int) = Bitmap.createScaledBitmap(imgCircle, circleWIdth - 50, circleHight - 50, true)


}