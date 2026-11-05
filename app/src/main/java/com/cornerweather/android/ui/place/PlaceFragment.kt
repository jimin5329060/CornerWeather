package com.cornerweather.android.ui.place
import android.R.attr.top
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log.v
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

import com.cornerweather.android.R
class PlaceFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBarLayout = view.findViewById<FrameLayout>(R.id.actionBarLayout)
        ViewCompat.setOnApplyWindowInsetsListener(actionBarLayout) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = top)
            insets
        }
        val bgImageView = view.findViewById<ImageView>(R.id.bgImageView)
        ViewCompat.setOnApplyWindowInsetsListener(bgImageView) { v, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            v.updatePadding(bottom = bottom)
            insets
        }

    }

}