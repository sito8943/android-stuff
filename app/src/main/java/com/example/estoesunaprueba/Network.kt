package com.example.estoesunaprueba

import okhttp3.OkHttpClient
import okhttp3.Request
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

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

interface ApiInterface {

    /**
     * request {
     *  idcliente: String
     *  nombre: String
     *  ci: String
     *  longitudo: Double
     *  latitudo: Double
     *  longitudd: Double
     *  latitudd: Double
     *  cantidad: Integer
     *  tipotaxi: Integer
     * }
     * response {
     *  choferes: List<Chofer>
     *  error: String
     * }
     */
    @POST("getsolicitataxi")
    fun getSolicitudTaxis(@Body requestModel: RequestSolicitudTaxis): Call<ResponseSolicitudTaxis>

    /**
     * request {
     *  idcliente: String
     *  idchofer: String
     * }
     * response {
     *  msg: String
     * }
     */
    @POST("geteligechofer")
    fun getEligeChofer(@Body requestModel: RequestEligeChofer): Call<ResponseEligeChofer>

    /**
     * request {
     *  idchofer: String
     *  idcliente: String
     * }
     * response {
     *  error: String
     * }
     */
    @POST("getcarreraok")
    fun getCarreraOk(@Body requestModel: RequestCarreraOk): Call<ResponseCarreraOk>

    /**
     * request
     * body {
     *  idcliente: String
     *  password: String
     *  longitud: Double
     *  latitud: Double
     * }
     * response
     * body {
     *  error: String
     * }
     */
    @POST("getlogincliente")
    fun getLoginCliente(@Body requestModel: RequestLoginCliente): Call<ResponseLoginCliente>

    /**
     * request
     * body {
     *  idchofer: String
     *  password: String
     *  longitud: Double
     *  latitud: Double
     * }
     * response
     * body {
     *  error: String
     * }
     */
    @POST("getloginchofer")
    fun getLoginChofer(@Body request: RequestLoginChofer): Call<ResponseLoginChofer>


}

// requests
data class RequestSolicitudTaxis(
    val idcliente: String,
    val nombre: String,
    val ci: String,
    val longitud: Double,
    val latitud: Double,
    val cantidad: Integer,
    val tipotaxi: Integer
)

data class RequestLoginChofer(
    val idchofer: String,
    val password: String,
    val longitud: Double,
    val latitud: Double,
)

data class RequestEligeChofer(
    val idcliente: String,
    val idchofer: String
)

data class RequestCarreraOk(
    val idchofer: String,
    val idcliente: String
)

data class RequestLoginCliente(
    val idcliente: String,
    val password: String,
    val longitud: Double,
    val latitud: Double,
)

data class Chofer(
    val idchofer: String,
    val longitud: Double,
    val latitud: Double,
    val ci: String,
    val phone: String,
    val tarifa: Integer,
)

// responses
data class ResponseSolicitudTaxis(
    val choferes: List<Chofer>,
    val error: String? = null
)

data class ResponseLoginChofer(
    val error: String? = null
)

data class ResponseEligeChofer(
    val msg: String
)

data class ResponseCarreraOk(
    val error: String? = null
)

data class ResponseLoginCliente(
    val error: String? = null
)