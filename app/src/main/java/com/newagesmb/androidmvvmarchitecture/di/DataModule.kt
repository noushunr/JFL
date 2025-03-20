package com.newagesmb.androidmvvmarchitecture.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.AppPreferences
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.DataStoreManager
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.AppApis
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val KEY_SHARED_PREFERENCES = "shared_prefs"

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppRepository(
        appApis: AppApis,
        preferences: AppPreferences,
        dataStoreManager: DataStoreManager,
        @ApplicationScope coroutineScope: CoroutineScope,
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ) = AppRepository(
        appApis,
        preferences,
        dataStoreManager,
        coroutineScope,
        mainDispatcher
    )
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context =
        context

    @Provides
    @Singleton
    fun provideStrings(@ApplicationContext context: Context)= context.resources
//    @Singleton
//    @Provides
//    fun provideDataStoreManager(app: Application): DataStoreManager {
//        return DataStoreManager(app)
//    }


}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PreferencesInterface {
    fun preferences(): AppPreferences
}



