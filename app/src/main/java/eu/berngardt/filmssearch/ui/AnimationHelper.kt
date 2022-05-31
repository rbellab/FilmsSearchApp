package eu.berngardt.filmssearch.ui

import kotlin.math.hypot
import android.view.View
import android.app.Activity
import kotlin.math.roundToInt
import java.util.concurrent.Executors
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator

object AnimationHelper {

    // Это константа для того, чтобы круг проявления расходился
    // именно от иконки меню навигации
    private const val MENU_ITEMS_COUNT = 4

    // Коэффициенты для суперсложной матетатики вычисления старта анимации
    private const val COMPLEX_MATH_COEFF_ONE = 1
    private const val COMPLEX_MATH_COEFF_TWO = 2

    // Время анимации
    private const val ANIMATION_DURATION = 500L


    // В метод у нас приходит 3 параметра:
    // 1 - наше rootView, которое одновременно является и контейнером
    // и объектом анимации
    // 2 - активити для того, чтобы вернуть выполнение нового треда в UI поток
    // 3 - позиция в меню навигации, чтобы круг проявления расходился именно от иконки меню навигации
    fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
        // Создаем новый тред
        Executors.newSingleThreadExecutor().execute {
            // В бесконечном цикле проверяем, когда наше анимированное view
            // будет "прикреплено" к экрану
            while (true) {
                // Когда оно будет прикреплено, выполним код
                if (rootView.isAttachedToWindow) {
                    // Возвращаемся в главный тред, чтобы выполнить анимацию
                    activity.runOnUiThread {
                        //  Cуперсложная математика вычисления старта анимации
                        val itemCenter = rootView.width / (MENU_ITEMS_COUNT * COMPLEX_MATH_COEFF_TWO)
                        val step = (itemCenter * 2) * (position - COMPLEX_MATH_COEFF_ONE) + itemCenter

                        val x: Int = step
                        val y: Int = rootView.y.roundToInt() + rootView.height

                        val startRadius = 0
                        val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())
                        // Создаем саму анимацию
                        ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                            // Устанавливаем время анимации
                            duration = ANIMATION_DURATION
                            // Интерполятор для более естественной анимации
                            interpolator = AccelerateDecelerateInterpolator()
                            // Запускаем
                            start()
                        }
                        // Выставляем видимость нашего элемента
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}