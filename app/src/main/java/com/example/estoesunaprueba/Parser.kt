package com.example.estoesunaprueba

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Parser {


    companion object {
        fun parseSms(context: Context, sms: String, sender: String): String {
            if (sms.length > 5 && sms.first() !== '@' && sms.subSequence(
                    sms.length - 2, sms.length
                ) !== "/@"
            ) return "wrong"
            Log.d("M2G PARSER SMS", sms)
            when (sms[1]) {
                '1' -> cambiapw(sms.subSequence(2, sms.length - 2))
                '2' -> solicitataxi(sms.subSequence(2, sms.length - 2))
                '3' -> logintaxi(sms.subSequence(2, sms.length - 2))
                '4' -> eligechofer(sms.subSequence(2, sms.length - 2))
                '5' -> carreraok(sms.subSequence(2, sms.length - 2))
                '6' -> cancelarcarrera(sms.subSequence(2, sms.length - 2))
                '7' -> cambiaestado(sms.subSequence(2, sms.length - 2))
                '8' -> registrarchofer(context,sms.subSequence(2, sms.length - 2), sender)
                '9' -> cambiaestado(sms.subSequence(2, sms.length - 2))
            }
            return "ok"
        }

        private fun cambiapw(content: CharSequence) {
            val splited = content.split(";")
            var idusuario: String = ""
            var pwa: String = ""
            var pwn: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "U" -> idusuario = subsplited[1]
                        "P" -> pwa = subsplited[1]
                        "N" -> pwn = subsplited[1]
                    }
                }
            }
            if (idusuario.isNotEmpty() && pwa.isNotEmpty() && pwn.isNotEmpty()) {
                val requestModel = RequestCambiaPW(idusuario, pwa, pwn)
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.cambiapw(requestModel).enqueue(object : Callback<ResponseCambiaPW> {
                    override fun onResponse(
                        call: Call<ResponseCambiaPW>, responsea: Response<ResponseCambiaPW>
                    ) {

                        Log.d("M2G PARSER", responsea.body().toString().toString())

                    }

                    override fun onFailure(call: Call<ResponseCambiaPW>, t: Throwable) {
                        Log.d("M2G Parser", "ERROR")
                        Log.d("M2G Parser", t.toString())

                    }

                })
            }
        }


        // 2
        private fun solicitataxi(content: CharSequence) {
            val splited = content.split(";")
            var origen: String = ""
            var destino: String = ""
            var cantidad: String = ""
            var tipotaxi: String = ""
            var nombre: String = ""
            var ci: String = ""
            var phone: String = ""
            var idcliente: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "O" -> origen = subsplited[1]
                        "D" -> destino = subsplited[1]
                        "C" -> cantidad = subsplited[1]
                        "T" -> tipotaxi = subsplited[1]
                        "N" -> nombre = subsplited[1]
                        "I" -> ci = subsplited[1]
                        "P" -> phone = subsplited[1]
                        "U" -> idcliente = subsplited[1]
                    }
                }
            }
            if (origen.isNotEmpty() && destino.isNotEmpty() && cantidad.isNotEmpty() && tipotaxi.isNotEmpty()) {
                val requestModel =
                    RequestSolicitaTaxis(
                        origen,
                        destino,
                        cantidad.toInt(),
                        tipotaxi.toInt(),
                        nombre,
                        ci,
                        phone,
                        idcliente
                    )
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.solicitaTaxis(requestModel)
                    .enqueue(object : Callback<ResponseSolicitaTaxis> {
                        override fun onResponse(
                            call: Call<ResponseSolicitaTaxis>,
                            responsea: Response<ResponseSolicitaTaxis>
                        ) {

                            Log.d("M2G PARSER", responsea.body().toString().toString())

                        }

                        override fun onFailure(call: Call<ResponseSolicitaTaxis>, t: Throwable) {
                            Log.d("M2G Parser", "ERROR")
                            Log.d("M2G Parser", t.toString())

                        }

                    })
            }

        }

        // 3
        private fun logintaxi(content: CharSequence) {
            Log.d("M2G PARSER", "LOGIN TAXTI FUNCTION")
            val splited = content.split(";")
            var idusuario: String = ""
            var password: String = ""
            var longitud: String = ""
            var latitud: String = ""
            var longitudd: String = ""
            var latitudd: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "U" -> idusuario = subsplited[1]
                        "P" -> password = subsplited[1]
                        "Y" -> longitud = subsplited[1]
                        "X" -> latitud = subsplited[1]
                        "W" -> longitudd = subsplited[1]
                        "Z" -> latitudd = subsplited[1]
                    }
                }
            }
            if (idusuario.isNotEmpty() && password.isNotEmpty() && longitud.isNotEmpty() && latitud.isNotEmpty()) {
                val requestModel =
                    RequestLoginTaxi(idusuario, password, longitud.toDouble(), latitud.toDouble())
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.loginTaxi(requestModel).enqueue(object : Callback<ResponseLoginTaxi> {
                    override fun onResponse(
                        call: Call<ResponseLoginTaxi>, responsea: Response<ResponseLoginTaxi>
                    ) {

                        Log.d("M2G PARSER", responsea.body().toString().toString())

                    }

                    override fun onFailure(call: Call<ResponseLoginTaxi>, t: Throwable) {
                        Log.d("M2G Parser", "ERROR")
                        Log.d("M2G Parser", t.toString())

                    }

                })
            }
        }


        // 4
        private fun eligechofer(content: CharSequence) {
            val splited = content.split(";")
            var idcliente: String = ""
            var idchofer: String = ""
            var origen: String = ""
            var destino: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "U" -> idcliente = subsplited[1]
                        "S" -> idchofer = subsplited[1]
                        "O" -> origen = subsplited[1]
                        "D" -> destino = subsplited[1]
                    }
                }
            }
            if (idcliente.isNotEmpty() && idchofer.isNotEmpty() && origen.isNotEmpty() && destino.isNotEmpty()) {
                val requestModel = RequestEligeChofer(idcliente, idchofer, origen, destino)
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.eligeChofer(requestModel).enqueue(object : Callback<ResponseEligeChofer> {
                    override fun onResponse(
                        call: Call<ResponseEligeChofer>,
                        responsea: Response<ResponseEligeChofer>
                    ) {
                        Log.d("M2G PARSER", responsea.body().toString().toString())
                    }

                    override fun onFailure(call: Call<ResponseEligeChofer>, t: Throwable) {
                        Log.d("M2G Parser", "ERROR")
                        Log.d("M2G Parser", t.toString())
                    }

                })
            }

        }

        // 5
        private fun carreraok(content: CharSequence) {
            val splited = content.split(";")
            var keycontrato: String = ""

            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "K" -> keycontrato = subsplited[1]

                    }
                }
            }
            if (keycontrato.isNotEmpty()) {
                val requestModel = RequestCarreraOk(keycontrato.toInt())
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.carreraOk(requestModel).enqueue(object : Callback<ResponseCarreraOk> {
                    override fun onResponse(
                        call: Call<ResponseCarreraOk>, responsea: Response<ResponseCarreraOk>
                    ) {
                        Log.d("M2G PARSER", responsea.body().toString().toString())
                    }

                    override fun onFailure(call: Call<ResponseCarreraOk>, t: Throwable) {
                        Log.d("M2G Parser", "ERROR")
                        Log.d("M2G Parser", t.toString())
                    }

                })
            }

        }

        // 6
        private fun cancelarcarrera(content: CharSequence) {
            val splited = content.split(";")
            var chofercliente: String = ""
            var keycontrato: String = ""
            var causa: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "V" -> chofercliente = subsplited[1]
                        "K" -> keycontrato = subsplited[1]
                        "Q" -> causa = subsplited[1]
                    }
                }
            }
            if (chofercliente.isNotEmpty() && keycontrato.isNotEmpty() && causa.isNotEmpty()) {
                val requestModel =
                    RequestCancelarCarrera(chofercliente.toInt(), keycontrato.toInt(), causa)
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.cancelarCarrera(requestModel)
                    .enqueue(object : Callback<ResponseCancelarCarrera> {
                        override fun onResponse(
                            call: Call<ResponseCancelarCarrera>,
                            responsea: Response<ResponseCancelarCarrera>
                        ) {
                            Log.d("M2G PARSER", responsea.body().toString().toString())
                        }

                        override fun onFailure(call: Call<ResponseCancelarCarrera>, t: Throwable) {
                            Log.d("M2G Parser", "ERROR")
                            Log.d("M2G Parser", t.toString())
                        }

                    })
            }

        }


        // 7
        private fun cambiaestado(content: CharSequence) {
            val splited = content.split(";")
            var idchofer: String = ""
            var estado: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "U" -> idchofer = subsplited[1]
                        "E" -> estado = subsplited[1]
                    }
                }
            }
            if (idchofer.isNotEmpty() && estado.isNotEmpty()) {
                val requestModel = RequestCambiaEstado(idchofer, estado)
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.cambiaEstado(requestModel)
                    .enqueue(object : Callback<ResponseCambiaEstado> {
                        override fun onResponse(
                            call: Call<ResponseCambiaEstado>,
                            responsea: Response<ResponseCambiaEstado>
                        ) {
                            Log.d("M2G PARSER", responsea.body().toString().toString())
                        }

                        override fun onFailure(call: Call<ResponseCambiaEstado>, t: Throwable) {
                            Log.d("M2G Parser", "ERROR")
                            Log.d("M2G Parser", t.toString())
                        }

                    })
            }

        }


        // 8
        private fun registrarchofer(context: Context, content: CharSequence, sender: String) {
            val splited = content.split(";")
            var nombre: String = ""
            var ci: String = ""
            var phone: String = ""
            var chapa: String = ""
            var tarifa: String = ""
            var desc: String = ""
            var tipo: String = ""
            var capacidad: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "N" -> nombre = subsplited[1]
                        "I" -> ci = subsplited[1]
                        "P" -> phone = subsplited[1]
                        "A" -> chapa = subsplited[1]
                        "F" -> tarifa = subsplited[1]
                        "D" -> desc = subsplited[1]
                        "T" -> tipo = subsplited[1]
                        "C" -> capacidad = subsplited[1]
                    }
                }
            }
            if (nombre.isNotEmpty() && ci.isNotEmpty() && phone.isNotEmpty() && chapa.isNotEmpty() && tarifa.isNotEmpty() && desc.isNotEmpty() && tipo.isNotEmpty() && capacidad.isNotEmpty()) {
                val requestModel = RequestRegistrarChofer(
                    nombre,
                    ci,
                    phone,
                    chapa,
                    tarifa.toDouble(),
                    desc,
                    tipo.toInt(),
                    capacidad.toInt()
                )
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.registrarChofer(requestModel)
                    .enqueue(object : Callback<ResponseRegistrarChofer> {
                        override fun onResponse(
                            call: Call<ResponseRegistrarChofer>,
                            responsea: Response<ResponseRegistrarChofer>
                        ) {

                            Log.d("M2G PARSER", responsea.body().toString().toString())
                            SMSSender.sendSMS(context, "@8U=" + (responsea.body()?.idusuario ?: "") + "/@", sender)

                        }

                        override fun onFailure(call: Call<ResponseRegistrarChofer>, t: Throwable) {
                            Log.d("M2G Parser", "ERROR")
                            Log.d("M2G Parser", t.toString())

                        }

                    })
            }

        }


        // 9
        private fun clienteespera(content: CharSequence) {
            val splited = content.split(";")
            var idcliente: String = ""
            var espera: String = ""
            splited.forEach {
                val subsplited = it.split("=")
                if (subsplited.size == 2) {
                    when (subsplited[0]) {
                        "C" -> idcliente = subsplited[1]
                        "E" -> espera = subsplited[1]
                    }
                }
            }
            if (idcliente.isNotEmpty() && espera.isNotEmpty()) {
                val requestModel = RequestClienteEspera(idcliente, espera.toInt())
                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.clienteEspera(requestModel)
                    .enqueue(object : Callback<ResponseClienteEspera> {
                        override fun onResponse(
                            call: Call<ResponseClienteEspera>,
                            responsea: Response<ResponseClienteEspera>
                        ) {

                            Log.d("M2G PARSER", responsea.body().toString().toString())

                        }

                        override fun onFailure(call: Call<ResponseClienteEspera>, t: Throwable) {
                            Log.d("M2G Parser", "ERROR")
                            Log.d("M2G Parser", t.toString())

                        }

                    })
            }

        }


    }
}