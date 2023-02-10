package com.dk.picasaw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.picasaw.models.RandomImg
import com.dk.picasaw.remote.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel: ViewModel() {
//    val randomImages = MutableLiveData<List<RandomImg>>()
//    val errormsg = MutableLiveData<String>()
  //  val isLoading = MutableLiveData<Boolean>()

    lateinit var repository:Repository

    fun requestRandomImages(count:Int,orientation:String,repository: Repository) {
        this.repository=repository
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchRandomImages(count,orientation)
        }
    }
    fun requestBannerImages(repository: Repository) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchBannerImages()
        }
    }
    fun requestAllImages(respository:Repository,page_num:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.fetchAllImages(page_num)
        }
    }
    fun requestSearchImages(respository:Repository,page_num:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.fetchSearchImages(page_num)
        }
    }
    val randomImages:LiveData<List<RandomImg>> get() = repository.randomImages
    val bannerImages get() = repository.bannerImages
}

//    fun getImgs():MutableLiveData<List<RandomImg>>{
//        //1st way
////        viewModelScope.launch {
////            val response=Repository().getRandomImg()
////            response.enqueue(object: Callback<List<RandomImg>> {
////                override fun onResponse(call: Call<List<RandomImg>>, response: Response<List<RandomImg>>) {
////                    randomImages.postValue(response.body())
////                    isLoading.value=false
////                }
////
////                override fun onFailure(call: Call<List<RandomImg>>, t: Throwable) {
////                    errormsg.value="Error: ${t.message}\nTry Again..."
////                    isLoading.value=false
////                }
////            })
////        }
//        //2nd way
//        CoroutineScope(Dispatchers.IO).launch {
//            val callResponse=Repository().getRandomImg()
//            val resp=callResponse.execute()
//            if (resp.isSuccessful)
//            {
//                randomImages.postValue(resp.body())
//            }
//            else{
//                errormsg.value="Error: ${resp.message()}\nTry Again..."
//            }
//        }
//        return randomImages
//    }








