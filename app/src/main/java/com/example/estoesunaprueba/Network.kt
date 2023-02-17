package com.example.estoesunaprueba

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class Network {}

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder().baseUrl("http://192.168.1.102:3001/") //
        .addConverterFactory(GsonConverterFactory.create()).client(client).build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

interface ApiInterface {

    /**
     * request {
     *  idusuario: String
     *  pwa: String
     *  pwn: String
     * }
     * response {
     *  error: String
     * }
     * @see 1
     */
    @POST("cambiapw")
    fun cambiapw(@Body requestModel: RequestCambiaPW): Call<ResponseCambiaPW>

    /**
     * request {
     *  idcliente: String
     *  origen: String
     *  destino: String
     *  nombre: String
     *  ci: String
     *  phone: String
     *  cantidad: Int
     *  tipotaxi: Int
     * }
     * response {
     *  choferes: List<Chofer>
     *  idcliente: String
     *  error: String
     * }
     * @see 2
     */
    @POST("solicitataxi")
    fun solicitaTaxis(@Body requestModel: RequestSolicitaTaxis): Call<ResponseSolicitaTaxis>

    /**
     * request {
     *  idusuario: String
     *  password: String
     *  longitud: Double
     *  latitud: Double
     *  longitudd: Double
     *  latitudd: Double
     * }
     * response {
     *  error: String
     * }
     * @see 3
     */
    @POST("logintaxi")
    fun loginTaxi(@Body requestModel: RequestLoginTaxi): Call<ResponseLoginTaxi>

    /**
     * request {
     *  idcliente: String
     *  idchofer: String
     *  origen: String
     *  destino: String
     * }
     * response {
     *  keycontrato: Int
     *  error: String
     * }
     * @see 4
     */
    @POST("eligechofer")
    fun eligeChofer(@Body requestModel: RequestEligeChofer): Call<ResponseEligeChofer>

    /**
     * request {
     *  keycontrato: Int
     * }
     * response {
     *  error: String
     * }
     * @see 5
     */
    @POST("carreraok")
    fun carreraOk(@Body requestModel: RequestCarreraOk): Call<ResponseCarreraOk>

    /**
     * request {
     *  chofercliente: Int
     *  keycontrato: Int
     *  causa: String
     * }
     * response {
     *  error: String
     * }
     * @see 6
     */
    @POST("cancelarcarrera")
    fun cancelarCarrera(@Body requestModel: RequestCancelarCarrera): Call<ResponseCancelarCarrera>

    /**
     * request {
     *  idchofer: String
     *  estado: Int
     * }
     * response {
     *  error: String
     * }
     *
     * @see 7
     */
    @POST("cambiaestado")
    fun cambiaEstado(@Body requestModel: RequestCambiaEstado): Call<ResponseCambiaEstado>

    /**
     * request {
     *  nombre: String
     *  ci: String
     *  phone: String
     *  chapa: String
     *  tarifa: Double
     *  desc: String
     *  tipo: Int
     *  cantidad: Int
     * }
     * response {
     *  idusuario: String
     *  error: String
     * }
     * @see 8
     */
    @POST("registrarchofer")
    fun registrarChofer(@Body requestModel: RequestRegistrarChofer): Call<ResponseRegistrarChofer>

    /**
     * request {
     *  idcliente: String
     *  espera: Int
     * }
     * response {
     *  error: String
     * }
     * @see 9
     */
    @POST("clienteespera")
    fun clienteEspera(@Body requestModel: RequestClienteEspera): Call<ResponseClienteEspera>


}


// requests


// 1
data class RequestCambiaPW(
    val idusuario: String, val pwa: String, val pwn: String
)

// 2
data class RequestSolicitaTaxis(
    val origen: String,
    val destino: String,
    val cantidad: Int,
    val tipotaxi: Int,
    val nombre: String? = null,
    val ci: String? = null,
    val phone: String? = null,
    val idcliente: String? = null,

)

// 3
data class RequestLoginTaxi(
    val idusuario: String,
    val password: String,
    val longitud: Double,
    val latitud: Double,
    val longitudd: Double? = null,
    val latitudd: Double? = null,
)

// 4
data class RequestEligeChofer(
    val idcliente: String, val idchofer: String, val origen: String, val destino: String
)

// 5
data class RequestCarreraOk(
    val keycontrato: Int
)

// 6
data class RequestCancelarCarrera(
    val chofercliente: Int, val keycontrato: Int, val causa: String
)

// 7
data class RequestCambiaEstado(
    val idchofer: String, val estado: String
)

// 8
data class RequestRegistrarChofer(
    val nombre: String,
    val ci: String,
    val phone: String,
    val chapa: String,
    val tarifa: Double,
    val desc: String,
    val tipo: Int,
    val capacidad: Int
)

// 9
data class RequestClienteEspera(
    val idcliente: String, val espera: Int
)

data class Chofer(
    val idchofer: String,
    val longitud: Double,
    val latitud: Double,
    val ci: String,
    val phone: String,
    val tarifa: Int,
)

// responses
data class ResponseSolicitaTaxis(
    val choferes: List<Chofer>, val idcliente:String, val error: String? = null
)

data class ResponseRegistrarChofer(
    val idusuario: String, val error: String? = null
)

data class ResponseEligeChofer(
    val keycontrato: String, val error: String? = null
)

data class ResponseCarreraOk(
    val error: String? = null
)

data class ResponseLoginTaxi(
    val error: String? = null
)

data class ResponseCancelarCarrera(
    val error: String? = null
)

data class ResponseCambiaPW(
    val error: String? = null
)

data class ResponseCambiaEstado(
    val error: String? = null
)

data class ResponseClienteEspera(
    val error: String? = null
)