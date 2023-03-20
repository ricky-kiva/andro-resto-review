package com.rickyslash.restoreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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