package com.rajkumarrajan.latesttechexample.Retrofit

import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    private var retrofit: Retrofit? = null
    private var retrofitGoogle: Retrofit? = null
    private val okHttpClient = OkHttpClient.Builder().connectTimeout(90, TimeUnit.SECONDS)
        .build()


    fun getInstance(): RetrofitInterface {
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit!!.create(RetrofitInterface::class.java)
    }


    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val BASE_URL_List = "https://jsonplaceholder.typicode.com/"
    private val httpClient = OkHttpClient()
    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private val builder1 = Retrofit.Builder()
        .baseUrl(BASE_URL_List)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient).build()
        return retrofit.create(serviceClass)
    }

    fun <S> createService1(serviceClass: Class<S>): S {
        val retrofit = builder1.client(httpClient).build()
        return retrofit.create(serviceClass)
    }


    fun <S> createServiceHeader(
        serviceClass: Class<S>, packageName: String,
        SHA1: String
    ): S {

        val okClient = OkHttpClient.Builder()
            .addInterceptor(
                object : Interceptor {
                    @NonNull
                    @Throws(IOException::class)
                    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
                        val original = chain.request()

                        // Request customization: add request headers
                        val requestBuilder = original.newBuilder()
                            .header("X-Android-Package", packageName)
                            .header("X-Android-Cert", SHA1)
                            .method(original.method(), original.body())

                        val request = requestBuilder.build()
                        return chain.proceed(request)
                    }
                })
            .build()

        val retrofit = builder.client(okClient).build()
        return retrofit.create(serviceClass)
    }

}