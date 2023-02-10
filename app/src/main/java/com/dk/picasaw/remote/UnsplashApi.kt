package com.dk.picasaw.remote

import com.dk.picasaw.models.RandomImg
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface UnsplashApi {
//    @Headers("Authorization: Client-ID glaeMp9xw9jwDrSzy9oVG1WpLzXZpzE7SdPkCXvxeZs")
//    @GET("photos/random?count=20") sHroRHXiGzbwFnaFh3DFRnq5VhDbajygZH-q0vsrT_E
//    fun randomImg(): Call<List<RandomImg>>

    @Headers("Authorization: Client-ID glaeMp9xw9jwDrSzy9oVG1WpLzXZpzE7SdPkCXvxeZs")
    @GET("photos")
    suspend fun allImages(
        @Query("page") page:Int,
        @Query("per_page") per_page:Int,
    ): Response<List<RandomImg>>

    @Headers("Authorization: Client-ID glaeMp9xw9jwDrSzy9oVG1WpLzXZpzE7SdPkCXvxeZs")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") page:Int,
        @Query("per_page") per_page:Int,
    ): Response<List<RandomImg>>

    @Headers("Authorization: Client-ID glaeMp9xw9jwDrSzy9oVG1WpLzXZpzE7SdPkCXvxeZs")
    @GET("photos/random")
    suspend fun randomImages(
        @Query("count") count:Int,
        @Query("orientation") orientation:String
    ): Response<List<RandomImg>>
}

//    @GET("recipes/random")
//    fun apiresponse(
//        @Query("apiKey") apiKey: String?,
//        @Query("number") number: String?,
//        @Query("tags") tags: List<String?>?
//    ): Call<ApiResponse?>?
//
//    @GET("recipes/{id}/information")
//    fun recipedetailresponse(
//        @Path("id") id: Int,
//        @Query("apiKey") apiKey: String?
//    ): Call<RecipeDetailResponse?>?
//
//    @GET("recipes/{id}/similar")
//    fun SIMILAR_RECIPE_RESPONSE_CALL(
//        @Path("id") id: Int,
//        @Query("number") number: String?,
//        @Query("apiKey") apiKey: String?
//    ): Call<List<SimilarRecipeResponse?>?>?

//@GET("recipes/random")
//Call<ApiResponse> apiresponse(
//@Query("apiKey") String apiKey,
//@Query("number") String number,
//@Query("tags") List<String> tags
//);
//
//@GET("recipes/{id}/information")
//Call<RecipeDetailResponse> recipedetailresponse(
//@Path("id") int id,
//@Query("apiKey") String apiKey
//);
//
//@GET("recipes/{id}/similar")
//Call<List<SimilarRecipeResponse>> SIMILAR_RECIPE_RESPONSE_CALL(
//@Path("id") int id,
//@Query("number") String number,
//@Query("apiKey") String apiKey
//);

