package com.rickyslash.restoreview
import retrofit2.Call
import retrofit2.http.*

// 'interface' to request Api for link such 'https://restaurant-api.dicoding.dev/detail/{id}'
interface ApiService {
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>
}