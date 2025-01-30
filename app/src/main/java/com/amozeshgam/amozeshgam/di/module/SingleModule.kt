package com.amozeshgam.amozeshgam.di.module

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.exoplayer.ExoPlayer
import com.amozeshgam.amozeshgam.data.api.AmozeshgamApiInterface
import com.amozeshgam.amozeshgam.data.api.DaneshjooyarApiInterface
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.di.qualifier.DaneshjooyarApi
import com.amozeshgam.amozeshgam.handler.SecurityHandler
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingleModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { context.preferencesDataStoreFile("amozeshgam") }
        )
    }

    @SuppressLint("HardwareIds")
    @Provides
    @Singleton
    fun provideMqttClient(
        @ApplicationContext context: Context,
        dataBaseInputOutput: DataBaseInputOutput,
        securityHandler: SecurityHandler,
    ): MqttAndroidClient {
        var userHash = ""
        CoroutineScope(Dispatchers.IO).launch {
            userHash = securityHandler.decryptData(
                dataBaseInputOutput.getData(DataStoreKey.hashDataKey) ?: byteArrayOf()
            )
        }
        return MqttAndroidClient(
            context,
            "tcp://services.amozeshgam.com:1883",
            userHash
        )
    }

    @Provides
    @Singleton
    fun provideAmozeshgamRetrofit(@ApplicationContext context: Context): AmozeshgamApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://api.amozeshgam.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().readTimeout(10000L, TimeUnit.MILLISECONDS).writeTimeout(
                    5000L,
                    TimeUnit.MILLISECONDS
                ).cache(Cache(File(context.cacheDir, "amozeshgam_cache"), (10 * 1024 * 1024)))
                    .build()
            )
            .build()
            .create(AmozeshgamApiInterface::class.java)
    }

    @DaneshjooyarApi
    @Provides
    @Singleton
    fun provideDaneshjooyarRetrofit(): DaneshjooyarApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://daneshjooyar.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DaneshjooyarApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideExoplayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }
}