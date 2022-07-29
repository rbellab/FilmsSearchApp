package eu.berngardt.filmssearch.view.customviews

import android.view.View
import android.graphics.*
import android.content.Context
import android.util.AttributeSet
import eu.berngardt.filmssearch.R

class RatingDonutView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    // Овал для рисования сегментов прогресс-бара
    private val oval = RectF()

    // Координаты центра View, а также Radius
    private var radius: Float = ZERRO_VALUE_FLOAT
    private var centerX: Float = ZERRO_VALUE_FLOAT
    private var centerY: Float = ZERRO_VALUE_FLOAT

    // Толщина линии прогресса
    private var stroke = DEFAULT_STROKE_VALUE

    // Значение прогресса от 0 - 100
    private var progress = DEFAULT_PROGRESS_VALUE

    // Значения размера текста внутри кольца
    private var scaleSize = DEFAULT_SCALE_SIZE_VALUE

    // Краски для фигур
    private lateinit var strokePaint: Paint
    private lateinit var digitPaint: Paint
    private lateinit var circlePaint: Paint

    init {
        // Получаем атрибуты и устанвливаем их в соответствующие поля
        val a = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RatingDonutView,
            ZERRO_VALUE_INT,
            ZERRO_VALUE_INT
        )

        try {
            stroke = a.getFloat(
                R.styleable.RatingDonutView_stroke,
                stroke
            )
            progress = a.getInt(
                R.styleable.RatingDonutView_progress,
                progress
            )
        } finally {
            a.recycle()
        }

        // Инициализируем первоначальные краски
        initPaint()
    }

    private fun initPaint() {
        // Краска для колец
        strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            // Сюда кладем значение из поля класса, потому как у нас краски будут видоизменяться
            strokeWidth = stroke
            // Цвет мы тоже будем получать в специальном метода, потому что в зависимости от рейтинга
            // мы будем менять цвет нашего кольца
            color = getPaintColor(progress)
            isAntiAlias = true
        }

        // Краска для цифр
        digitPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = DEFAULT_STROKE_WIDTH
            setShadowLayer(
                RATING_DIGIT_SHADOW_RADIUS,
                ZERRO_VALUE_FLOAT,
                ZERRO_VALUE_FLOAT,
                Color.DKGRAY
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(DEFAULT_STROKE_WIDTH)
        } else {
            width.div(DEFAULT_STROKE_WIDTH)
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
        centerX = minSide.div(DEFAULT_STROKE_WIDTH)
        centerY = minSide.div(DEFAULT_STROKE_WIDTH)

        setMeasuredDimension(minSide, minSide)
    }

    private fun chooseDimension(mode: Int, size: Int) =
        when (mode) {
            MeasureSpec.AT_MOST,
            MeasureSpec.EXACTLY -> size
            else -> DEFAULT_DEMENSION
        }

    override fun onDraw(canvas: Canvas) {
        // Рисуем кольцо и задний фон
        drawRating(canvas)
        // Рисуем цифры
        drawText(canvas)
    }

    fun setProgress(pr: Int) {
        // Кладем новове значение в нашу поле класса
        progress = pr
        // Создаем краски с новыми цветами
        initPaint()
        // Вызываем перерисовку View
        invalidate()
    }

    private fun drawRating(canvas: Canvas) {
        // Здесь мы можем регулировать размер нашего кольца
        val scale = radius * RATING_RADIUS_COEFF
        // Сохраняем канвас
        canvas.save()
        // Перемещаем нулевые координаты канваса в центр, вы помните, так проще рисовать все круглое
        canvas.translate(
            centerX,
            centerY
        )
        // Устанавливаем размеры под наш овал
        oval.set(
            ZERRO_VALUE_FLOAT - scale,
            ZERRO_VALUE_FLOAT - scale,
            scale ,
            scale
        )
        // Рисуем задний фон(Желательно его отрисовать один раз в bitmap, так как он статичный)
        canvas.drawCircle(
            ZERRO_VALUE_FLOAT,
            ZERRO_VALUE_FLOAT,
            radius,
            circlePaint
        )
        // Рисуем "арки" из них и будет состоять наше кольцо + у нас тут специальный метод
        canvas.drawArc(
            oval,
            START_ANGLE,
            convertProgressToDegrees(progress),
            false,
            strokePaint
        )
        // Восстанавливаем канвас
        canvas.restore()
    }

    private fun drawText(canvas: Canvas) {
        // Форматируем текст, чтобы мы выводили дробное число с одной цифрой после точки
        val message = String.format("%.1f", progress / DEFAULT_STROKE_VALUE)

        // Получаем ширину и высоту текста, чтобы компенсировать их при отрисовке,
        // что бы текст был точно в центре
        val widths = FloatArray(message.length)

        digitPaint.getTextWidths(message, widths)
        var advance = ZERRO_VALUE_FLOAT
        for (width in widths) advance += width

        // Рисуем наш текст
        canvas.drawText(
            message,
            centerX - advance / DRAW_TEXT_CALC_COEFF1,
            centerY  + advance / DRAW_TEXT_CALC_COEFF2,
            digitPaint
        )
    }

    private fun getPaintColor(progress: Int): Int = when(progress) {
        in BAD_RATING_RANGE -> Color.parseColor(BAD_RATING_COLOR)
        in LOW_RATING_RANGE -> Color.parseColor(LOW_RATING_COLOR)
        in MIDDLE_RATING_RANGE -> Color.parseColor(MIDDLE_RATING_COLOR)
        else -> Color.parseColor(HIGH_RATING_COLOR)
    }

    private fun convertProgressToDegrees(progress: Int): Float = progress * CALC_PROGRESS_COEFF1

    companion object {
        private const val ZERRO_VALUE_INT = 0
        private const val ZERRO_VALUE_FLOAT = 0.0F
        private const val DEFAULT_STROKE_WIDTH = 2.0F
        private const val DEFAULT_STROKE_VALUE = 10.0F
        private const val DEFAULT_PROGRESS_VALUE = 50
        private const val DEFAULT_SCALE_SIZE_VALUE = 60F
        private const val CALC_PROGRESS_COEFF1 = 3.6f
        private const val RATING_RADIUS_COEFF = 0.8F
        private const val RATING_DIGIT_SHADOW_RADIUS = 5f
        private const val BAD_RATING_COLOR = "#e84258"
        private const val LOW_RATING_COLOR = "#fd8060"
        private const val MIDDLE_RATING_COLOR = "#fee191"
        private const val HIGH_RATING_COLOR = "#b0d8a4"
        private const val DEFAULT_DEMENSION = 300
        private const val START_ANGLE = -90.0F
        private const val DRAW_TEXT_CALC_COEFF1 = 2
        private const val DRAW_TEXT_CALC_COEFF2 = 4
        private val BAD_RATING_RANGE = IntRange(0, 25)
        private val LOW_RATING_RANGE = IntRange(26, 50)
        private val MIDDLE_RATING_RANGE = IntRange(51, 75)
    }
}
