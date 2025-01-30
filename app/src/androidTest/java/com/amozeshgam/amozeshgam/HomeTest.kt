package com.amozeshgam.amozeshgam

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: HomeActivityRepository

    @Before
    fun onStartTesting() = hiltRule.inject()

    @Test
    fun testApiGetHomeData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getHomeData()
            assertNotNull(response)
        }
    }

    @Test
    fun testApiGetPodcastsData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getPodcastsData()
            assertNotNull(response)
        }
    }

    @Test
    fun testApiGetPackagesData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCoursesData()
            assertNotNull(response)
        }
    }

    @Test
    fun testApiGetProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUserData(body = ApiRequestId(1))
            assertNotNull(response)
        }
    }
}