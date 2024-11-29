package com.example.retrofitandgson

import android.app.Application
import com.example.retrofitandgson.data.AppContainer
import com.example.retrofitandgson.data.DefaultAppContainer

class PokemonApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
