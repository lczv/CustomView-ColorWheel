package com.android.study.lczv.colorwheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ColorWheel:View,View.OnTouchListener{

    // Test Callback, return the value to the MainActivity
    var valueCallback:(text:String)-> Unit = {}

    var viewWidth = 0
    var viewHeight = 0

    var clickPoint = PointF(0f,0f)

    val paint = Paint()

    var selectedColor:Int = 0
    var hsvColor = floatArrayOf(1f,1f,1f)

    constructor(context: Context?) : super(context){
        init(null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){
        init(attrs)
    }


    fun init(attrs: AttributeSet?){
        setOnTouchListener(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(viewWidth,viewHeight)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.strokeWidth = 40f

        for(i in 0 until 360){
            paint.color = Color.HSVToColor(floatArrayOf(i.toFloat(),1f,1f))
            canvas?.drawArc(viewWidth/2f-400,viewHeight/2f-400,viewWidth/2f+400,viewHeight/2f+400,i.toFloat(),1f,true,paint)
        }

        // Draws the central circle
        paint.color = Color.WHITE
        canvas?.drawCircle(viewWidth/2f,viewHeight/2f,200f,paint)


        var angle = Math.atan2(
                (clickPoint.x.toDouble()-viewWidth/2),
                clickPoint.y.toDouble()-(viewHeight/2))
        angle = Math.toDegrees(angle)
        angle = ((-angle+360)+90)%360

        hsvColor = floatArrayOf(angle.toFloat(),1f,1f)
        selectedColor = Color.HSVToColor(hsvColor)
        paint.color = selectedColor

        canvas?.drawCircle(
                (viewWidth/2+Math.cos(Math.toRadians(angle)).toFloat()*290f),
                (viewHeight/2+Math.sin(Math.toRadians(angle)).toFloat()*290f),
                64f,
                paint)


        // Draws the color code
//        paint.color = Color.BLACK
//        paint.textSize = 96f
//        canvas?.drawText(
//                "R ${Color.red(Color.HSVToColor(hsvColor))} G ${Color.green(Color.HSVToColor(hsvColor))} B ${Color.blue(Color.HSVToColor(hsvColor))}"
//                ,244f
//                ,viewHeight/2f+512
//                ,paint)


        valueCallback(
                "R ${Color.red(Color.HSVToColor(hsvColor))} " +
                "G ${Color.green(Color.HSVToColor(hsvColor))} " +
                "B ${Color.blue(Color.HSVToColor(hsvColor))}"
        )
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        clickPoint.x = event!!.x
        clickPoint.y = event.y

        invalidate()

        return true

    }

    // Test Callback, return the value to the MainActivity
    fun setCallback(callback:(text:String)->Unit){
        this.valueCallback = callback
    }

}