package com.example.estoesunaprueba

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class Network {
}

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.102:3001/") //
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}

interface ApiInterface {

    @POST("getstring")
    fun sendReq(@Body requestModel: RequestModel) : Call<ResponseModel>
}

data class ResponseModel(
    val error: String
)

data class RequestModel(
    val mensaje: String,
)