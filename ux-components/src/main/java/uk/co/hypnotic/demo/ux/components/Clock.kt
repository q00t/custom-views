package uk.co.hypnotic.demo.ux.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class Clock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var currentSeconds = 0

    private val refreshFrequency : Long

    private val backgroundPaint = Paint()
    private val linePaint = Paint()
    private var centerX = 0f
    private var centerY = 0f
    private var endX = 0f
    private var endY = 0f

    private var lineLength = 0f

    private val backgroundRect = RectF()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Clock,
            0,
            0
        ).apply {

            try {
                refreshFrequency = getInt(R.styleable.Clock_updateFrequency, 1000).toLong()
                backgroundPaint.color = getColor(R.styleable.Clock_faceColour, Color.RED)
                linePaint.color = getColor(R.styleable.Clock_handColour, Color.BLACK)
                linePaint.strokeWidth = getDimensionPixelSize(R.styleable.Clock_handWidth, 10).toFloat()
            } finally {
                recycle()
            }

        }

    }

    fun setSeconds(seconds : Int) {
        currentSeconds = seconds
        update(seconds)
    }

    private fun update(seconds: Int) {
        val currentLineAngle = calculateCurrentLineAngle(seconds)

        if (0 <= currentLineAngle && currentLineAngle < 90) {
            calculateEndPointsInTheTopRight(currentLineAngle, lineLength)
        } else if (90 <= currentLineAngle && currentLineAngle < 180){
            calculateEndPointsInTheBottomRight(currentLineAngle, lineLength)
        } else if (180 <= currentLineAngle && currentLineAngle < 270) {
            calculateEndPointsInTheBottomLeft(currentLineAngle, lineLength)
        } else {
            calculateEndPointsInTheTopLeft(currentLineAngle, lineLength)
        }

        invalidate()
    }

    private fun calculateCurrentLineAngle(seconds: Int): Float {
        val lineAngle = (360f / 60f) * seconds.toFloat()

        Log.d("SH", "seconds: $seconds, angle: $lineAngle")

        return lineAngle
    }

    private fun calculateEndPointsInTheTopRight(angle : Float, length : Float) {
        Log.d("SH", "calculateEndPointsInTheTopRight: $angle")
        endX = centerX + length * sin(angle)
        endY = centerY - length * cos(angle)
    }

    private fun calculateEndPointsInTheBottomRight(angle : Float, length : Float) {
        Log.d("SH", "calculateEndPointsInTheBottomRight: $angle")
        endX = centerX + length * cos(angle - 90)
        endY = centerY + length * sin(angle - 90)
    }

    private fun calculateEndPointsInTheBottomLeft(angle : Float, length : Float) {
        Log.d("SH", "calculateEndPointsInTheBottomLeft: $angle")
        endX = centerX  - length * sin(angle - 180)
        endY = centerY + length * cos(angle - 180)
    }

    private fun calculateEndPointsInTheTopLeft(angle : Float, length : Float) {
        Log.d("SH", "calculateEndPointsInTheTopLeft: $angle")
        endX = centerX  - length * cos(angle - 270)
        endY = centerY - length * sin(angle - 270)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2.0f
        centerY = height / 2.0f
        lineLength = (min(width, height) / 2).toFloat()
        backgroundRect.set(centerX - lineLength, centerY - lineLength, centerX + lineLength, centerY + lineLength)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            update(currentSeconds)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(backgroundRect, backgroundPaint)

        canvas?.drawLine(
            centerX,
            centerY,
            endX,
            endY,
            linePaint
        )

    }

}