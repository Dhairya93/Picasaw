package com.dk.picasaw.remote

import android.util.Log
import com.dk.picasaw.models.RandomImg
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class ApiManager {
    companion object{
        val BASE_URL="https://api.unsplash.com/"
        fun getInstance():Retrofit {
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }
}