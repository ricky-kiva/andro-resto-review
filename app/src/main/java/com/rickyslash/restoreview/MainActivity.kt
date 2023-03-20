package com.rickyslash.restoreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rickyslash.restoreview.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        findRestaurant()

        binding.btnSend.setOnClickListener { view ->
            postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun findRestaurant() {
        showLoading(true)

        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun setReviewData(customerReviews: List<CustomerReviewsItem>) {
        val listReview = ArrayList<String>()
        for (review in customerReviews) {
            listReview.add("${review.name}: ${review.review}".trimIndent())
        }
        val adapter = ReviewAdapter(listReview)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun postReview(review: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Starlord", review)
        client.enqueue(object: Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    setReviewData(responseBody.customerReviews)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}

// Retrofit: library from 'SquareUp' to organize 'networking' to web API
// - Converter GSON: library to 'parse automatically'. 'Need' to be 'implemented along with Retrofit'

// list of annotation in Retrofit:
// - @Headers: add 'additional information' in 'header request' (Authorization, data type, etc)
// - @Query: 'input parameter' on method '@GET'
// - @Path: input 'variable' for 'endpoint' 'parameter'
// - @FormUrlEncoded: 'tag' function on '@POST' as 'form-url-encoded'
// - @Field: 'input parameter' on method '@POST'
// - @Multipart: 'tag' a function that it's a 'multipart'. 'multipart' means it 'contains binary & textual data' (image, file, etc)
// - @Part: 'specify' the part in '@Multipart'
// - @PartMap: 'send another data' beside 'file' in 'multipart'

// LoggingInterceptor: library from 'OkHttp' to 'debug response status' in 'Logcat'

// make sure 'LoggingInterceptor' ONLY shows on 'Debug Mode', so the information being sent isn't leaked
// to do that:
/*val loggingInterceptor = if(BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}    val loggingInterceptor = if(BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}*/

// Chucker: library to 'debug response status' 'directly from device'
// to do that:
/*val client = OkHttpClient.Builder()
    .addInterceptor(ChuckerInterceptor(context))
    .build()
val retrofit = Retrofit.Builder()
    .baseUrl("https://reqres.in/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()*/

// RoboPOJOGenerator: to automatically generate POJO (Kotlin data class from JSON)