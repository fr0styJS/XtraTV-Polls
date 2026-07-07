package com.github.andreyasadchy.xtra.util

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

val View.isKeyboardShown: Boolean
    get() {
        val rect = Rect()
        getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height

        // rect.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - rect.bottom
        return keypadHeight > screenHeight * 0.15
    }

// Call after TabLayoutMediator.attach()
fun ViewPager2.setupTvPagerFocus(tabLayout: TabLayout) {
    // Selecting a tab when it gains focus lets the D-pad switch pages without a click
    (tabLayout.getChildAt(0) as? ViewGroup)?.let { strip ->
        for (i in 0 until strip.childCount) {
            strip.getChildAt(i).setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) tabLayout.getTabAt(i)?.select()
            }
        }
    }
    // All pages stay attached (offscreenPageLimit = itemCount), so block D-pad focus
    // from landing inside pages that are not on screen
    (getChildAt(0) as? RecyclerView)?.let { recyclerView ->
        val update = {
            for (i in 0 until recyclerView.childCount) {
                (recyclerView.getChildAt(i) as? ViewGroup)?.let { page ->
                    val position = recyclerView.getChildAdapterPosition(page)
                    page.descendantFocusability = if (position == currentItem || position == RecyclerView.NO_POSITION) {
                        ViewGroup.FOCUS_BEFORE_DESCENDANTS
                    } else {
                        ViewGroup.FOCUS_BLOCK_DESCENDANTS
                    }
                }
            }
        }
        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) = update()
            override fun onChildViewDetachedFromWindow(view: View) {}
        })
        recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> update() }
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                post { update() }
            }
        })
        // Focus search can dead-end on the pager itself (it is a focusable scroll
        // container that overlaps its children) - push focus into the current page
        recyclerView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                update()
                (recyclerView.findViewHolderForAdapterPosition(currentItem)?.itemView as? ViewGroup)?.requestFocus()
            }
        }
    }
}

fun ViewPager2.reduceDragSensitivity() {
    try {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop * 2)
    } catch (e: Exception) {
    }
}