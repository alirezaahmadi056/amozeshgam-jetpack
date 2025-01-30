package com.amozeshgam.amozeshgam.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.amozeshgam.amozeshgam.view.screens.tour.ViewTour
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.tour.TourViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TourActivity : ComponentActivity() {
    private val viewModel: TourViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.finishActivity.observe(this){
            if (it){
                finish()
            }
        }
        setContent {
            AmozeshgamTheme {
                ViewTour()
            }
        }
    }
}
