package com.dk.picasaw.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dk.picasaw.models.RandomImg
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create

class Repository(private val api:UnsplashApi) {
    val randomImagesLivedata = MutableLiveData<List<RandomImg>>()
    val randomImages:LiveData<List<RandomImg>> get() = randomImagesLivedata
    var apiError: String="Successful..."
    var bannerImageLiveData=MutableLiveData<List<RandomImg>>()
    val bannerImages:LiveData<List<RandomImg>> get() = bannerImageLiveData
    val searchImagesLiveData=MutableLiveData<List<RandomImg>>()
    val searchImages:LiveData<List<RandomImg>> get() = searchImagesLiveData
    val allImagesLiveData=MutableLiveData<List<RandomImg>>()
    val allImages:LiveData<List<RandomImg>> get() = allImagesLiveData

    suspend fun fetchRandomImages(count:Int,orientation:String){
        try {
            val result = api.randomImages(count, orientation)
            if (result.isSuccessful) {
                val imgs = result.body()
                if (imgs != null) {
                    randomImagesLivedata.postValue(imgs)
                } else {
                    apiError = "No Images Loaded"
                }

            } else {
                apiError = result.body().toString()
            }
        }
        catch (exception:Exception){
            apiError= exception.message.toString()
        }
    }

    suspend fun fetchBannerImages(){
        try {
            val result = api.randomImages(5,"landscape")
            if (result.isSuccessful) {
                val bannerimgs = result.body()
                if (bannerimgs != null) {
                    bannerImageLiveData.postValue(bannerimgs)
                } else {
                    apiError = "No Images Loaded"
                }

            } else {
                apiError = result.body().toString()
            }
        }
        catch (exception:Exception){
            apiError= exception.message.toString()
        }
    }

    suspend fun fetchSearchImages(page_num:Int){
        try{
            val result=api.searchImages(page_num,30)
            if (result.isSuccessful){
                val searchImages=result.body()
                if(searchImages!=null){
                    searchImagesLiveData.postValue(searchImages)
                }else{apiError="No Images Loaded"}
            }else{apiError=result.body().toString()}
        }catch (e:Exception){e.printStackTrace()}
    }
    suspend fun fetchAllImages(page_num:Int){
        try{
            val result=api.allImages(page_num,30)
            if (result.isSuccessful){
                val allImages=result.body()
                if(allImages!=null){
                    allImagesLiveData.postValue(allImages)
                }else{apiError="No Images Loaded"}
            }else{apiError=result.body().toString()}
        }catch (e:Exception){e.printStackTrace()}
    }
}