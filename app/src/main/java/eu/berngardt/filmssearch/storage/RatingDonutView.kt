package eu.berngardt.filmssearch.storage

import android.view.View
import android.graphics.*
import android.content.Context
import android.util.AttributeSet
import eu.berngardt.filmssearch.R


class RatingDonutView
@JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {

    companion object {
        private const val BAD_RATING_COLOR = "#e84258"
        private const val LOW_RATING_COLOR = "#fd8060"
        private const val MIDLE_RATING_COLOR = "#fee191"
        private const val HIGH_RATING_COLOR = "#b0d8a4"
        private const val RATING_STROKE_WIDTH = 2f
        private const val RATING_DIGIT_SHADOW_RADIUS = 5f
        private const val RATING_DIGIT_SHADOW_DX = 0f
        private const val RATING_DIGIT_SHADOW_DY = 0f
        private const val RATING_DIGIT_SHADOW_COLOR = Color.DKGRAY
        private const val RATING_RADIUS_COEFF = 0.8f
        private const val CALC_SIZE_COEFF = 2f
        private const val CALC_PROGRESS_COEFF1 = 3.6f
        private const val CALC_PROGRESS_COEFF2 = 10f
        private const val DEFAULT_DEMENSION = 300
        private const val DEFAULT_RATING_ARC_ANGLE = -90f
        private const val FLOAT_ZERO_VALUE = 0f
        private const val DRAW_TEXT_COEFF1 = 2
        private const val DRAW_TEXT_COEFF2 = 4
    }

    // Овал для рисования сегментов прогресс бара
    private val oval = RectF()
    // Координаты центра View, а также Radius
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    // Толщина линии прогресса
    private var stroke = 10f
    // Значение прогресса от 0 - 100
    private var progress = 50
    // Значения размера текста внутри кольца
    private var scaleSize = 60f
    // Краски для наших фигур
    private lateinit var strokePaint: Paint
    private lateinit var digitPaint: Paint
    private lateinit var circlePaint: Paint

    private fun getPaintColor(progress: Int): Int = when(progress) {
        in 0 .. 25 -> Color.parseColor(BAD_RATING_COLOR)
        in 26 .. 50 -> Color.parseColor(LOW_RATING_COLOR)
        in 51 .. 75 -> Color.parseColor(MIDLE_RATING_COLOR)
        else -> Color.parseColor(HIGH_RATING_COLOR)
    }

    private fun initPaint() {
        // Краска для колец
        strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            // Сюда кладем значение из поля класса, потому как у нас краски будут видоизменяться
            strokeWidth = stroke
            // Цвет мы тоже будем получать в специальном методе, потому что в зависимости от рейтинга
            // мы будем менять цвет нашего кольца
            color = getPaintColor(progress)
            isAntiAlias = true
        }

        // Краска для цифр
        digitPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = RATING_STROKE_WIDTH
            setShadowLayer(
                RATING_DIGIT_SHADOW_RADIUS,
                RATING_DIGIT_SHADOW_DX,
                RATING_DIGIT_SHADOW_DY,
                RATING_DIGIT_SHADOW_COLOR
            )
            textSize = scaleSize
            typeface = Typeface.SANS_SERIF
            color = getPaintColor(progress)
            isAntiAlias = true
        }

        // Краска для заднего фона
        circlePaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.DKGRAY
        }
    }

    init {
        // Получаем атрибуты и устанавливаем их в соответствующие поля
        val a = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RatingDonutView,
            0, 0
        )
        try {
            stroke = a.getFloat(
                R.styleable.RatingDonutView_stroke, stroke)
            progress = a.getInt(R.styleable.RatingDonutView_progress, progress)
        } finally {
            a.recycle()
        }
        // Инициализируем первоначальные краски
        initPaint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(CALC_SIZE_COEFF)
        } else {
            width.div(CALC_SIZE_COEFF)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide.div(CALC_SIZE_COEFF)
        centerY = minSide.div(CALC_SIZE_COEFF)

        setMeasuredDimension(minSide, minSide)
    }

    private fun chooseDimension(mode: Int, size: Int) =
        when (mode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
            else -> DEFAULT_DEMENSION
        }

    private fun drawRating(canvas: Canvas) {
        // Здесь мы можем регулировать размер нашего кольца
        val scale = radius * RATING_RADIUS_COEFF
        // Сохраняем канвас
        canvas.save()
        // Перемещаем нулевые координаты канваса в центр, вы помните, так проще рисовать все круглое
        canvas.translate(centerX, centerY)
        // Устанавливаем размеры под наш овал
        oval.set(FLOAT_ZERO_VALUE - scale, FLOAT_ZERO_VALUE - scale, scale , scale)
        // Рисуем задний фон(Желательно его отрисовать один раз в bitmap, так как он статичный)
        canvas.drawCircle(FLOAT_ZERO_VALUE, FLOAT_ZERO_VALUE, radius, circlePaint)
        // Рисуем "арки", из них и будет состоять наше кольцо + у нас тут специальный метод
        canvas.drawArc(
            oval,
            DEFAULT_RATING_ARC_ANGLE,
            convertProgressToDegrees(progress),
            false,
            strokePaint)
        // Восстанавливаем канвас
        canvas.restore()
    }

    private fun convertProgressToDegrees(progress: Int): Float = progress * CALC_PROGRESS_COEFF1

    private fun drawText(canvas: Canvas) {
        // Форматируем текст, чтобы мы выводили дробное число с одной цифрой после точки
        val message = String.format("%.1f", progress / CALC_PROGRESS_COEFF2)
        // Получаем ширину и высоту текста, чтобы компенсировать их при отрисовке, чтобы текст был
        // точно в центре
        val widths = FloatArray(message.length)
        digitPaint.getTextWidths(message, widths)
        var advance = FLOAT_ZERO_VALUE
        for (width in widths) advance += width
        // Рисуем наш текст
        canvas.drawText(
            message,
            centerX - advance / DRAW_TEXT_COEFF1,
            centerY  + advance / DRAW_TEXT_COEFF2,
            digitPaint
        )
    }

    override fun onDraw(canvas: Canvas) {
        // Рисуем кольцо и задний фон
        drawRating(canvas)
        // Рисуем цифры
        drawText(canvas)
    }

    fun setProgress(pr: Int) {
        // Кладем новое значение в наше поле класса
        progress = pr
        // Создаем краски с новыми цветами
        initPaint()
        // вызываем перерисовку View
        invalidate()
    }
}