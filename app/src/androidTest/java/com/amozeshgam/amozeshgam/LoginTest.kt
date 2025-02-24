package com.amozeshgam.amozeshgam

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestPhone
import com.amozeshgam.amozeshgam.data.repository.LoginClusterRepository
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
class LoginTest {
    @get:Rule val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: LoginClusterRepository
    @Before
    fun onStartTesting() = hiltRule.inject()

    @Test
    fun testApiSendCode() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.sendCodeLoginApi(ApiRequestPhone("09106297135"))
            assertNotNull(response)
        }
    }
}