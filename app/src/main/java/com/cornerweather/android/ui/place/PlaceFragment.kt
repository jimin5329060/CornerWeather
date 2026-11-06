package com.cornerweather.android.ui.place
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornerweather.android.R
import com.cornerweather.android.logic.model.Location
import com.cornerweather.android.logic.model.Place

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
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val testList = listOf(
            Place(
                name = "Test Place 1",
                location = Location("114.05", "22.55"),
                address = "123 Test St, Test City"
            ),
            Place(
                name = "Test Place 2",
                location = Location("116.40", "39.90"),
                address = "456 Example Ave, Sample Town"
            ),
            Place(
                name = "Test Place 3",
                location = Location("121.47", "31.23"),
                address = "789 Demo Blvd, Mockville"
            )
        )
        recyclerView.adapter = PlaceAdapter(testList)

    }

}