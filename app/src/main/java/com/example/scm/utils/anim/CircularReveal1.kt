package com.example.scm.utils.anim


import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi

class CircularReveal1 {

    private var canAnim: Boolean
    private var expandDur: Int
    private var collapseDur: Int
    var offset = 0
    private var view: View? = null
    private var centerX = 0
    private var centerY = 0
    var isDuringAnim = false
        private set
    private var backgroundColor = Color.CYAN
    private val animationEnd = true
    private var keepViewStateAfterAnim = false
    private var circularRevealListener: CircularRevealListener? = null

    constructor(view: View?, centerX: Int, centerY: Int) {
        requireNotNull(view) { "Animated view can't be null!" }
        require(centerX >= 0) { "Invalid centerX: $centerX" }
        require(centerY >= 0) { "Invalid centerY: $centerY" }
        this.view = view
        this.centerX = centerX
        this.centerY = centerY
        collapseDur = 400
        expandDur = 500
        canAnim = true
    }

    constructor() {
        collapseDur = 400
        expandDur = 500
        canAnim = true
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun animate() {
        if (canAnim) {
            expand()
        } else {
            collapse()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun expand() {
        animate(EXPAND)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun collapse() {
        animate(COLLAPSE)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun animate(type: Int) {
        val collapse = type == COLLAPSE
        if (isDuringAnim) {
            return
        }
        val max = Math.max(view!!.width, view!!.height)
        val startRadius = if (collapse) max else 0
        val endRadius = if (collapse) 0 else max
        val anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius.toFloat(), endRadius.toFloat())
        anim.duration = expandDur.toLong()
        if (offset > 0) {
            anim.startDelay = offset.toLong()
        }
        view!!.visibility = View.VISIBLE
        if (!keepViewStateAfterAnim) {
            view!!.alpha = 1f
        }
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isDuringAnim = true
            }

            override fun onAnimationEnd(animator: Animator) {
                isDuringAnim = false
                if (circularRevealListener != null) {
                    circularRevealListener!!.onAnimationEnd(EXPAND)
                }
                if (type == EXPAND) {
                    if (!keepViewStateAfterAnim) {
                        fadeAnimation(view)
                    }
                } else {
                    view!!.visibility = View.INVISIBLE
                }
                canAnim = !canAnim
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        anim.start()
    }

    fun setCircularRevealListener(circularRevealListener: CircularRevealListener?) {
        this.circularRevealListener = circularRevealListener
    }

    fun setExpandDur(expandDur: Int) {
        this.expandDur = expandDur
    }

    fun setCollapseDur(collapseDur: Int) {
        this.collapseDur = collapseDur
    }

    fun setKeepViewStateAfterAnim(keepViewStateAfterAnim: Boolean) {
        this.keepViewStateAfterAnim = keepViewStateAfterAnim
    }

    fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        view!!.setBackgroundResource(backgroundColor)
    }

    interface CircularRevealListener {

        fun onAnimationEnd(animState: Int)
    }

    private fun fadeAnimation(v: View?) {
        val fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, .0f)
        fadeOut.duration = 500
        fadeOut.start()
    }

    fun setView(view: View?) {
        this.view = view
    }

    fun setCenterX(centerX: Int) {
        this.centerX = centerX
    }

    fun setCenterY(centerY: Int) {
        this.centerY = centerY
    }

    companion object {
        /*
     * Ida ou volta da animação.
     */
        const val COLLAPSE = 1
        const val EXPAND = 2
    }
}