package com.example.estoesunaprueba

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.estoesunaprueba.ui.theme.EstoEsUnaPruebaTheme

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1


    /**
     * Checks whether the app has SMS permission.
     */
    private fun checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "No Granted")
            /* Toast.makeText(
                this, "Permisos Mal",
                Toast.LENGTH_LONG
            ).show() */

            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECEIVE_SMS),
                MY_PERMISSIONS_REQUEST_RECEIVE_SMS
            )
        } else {
            // Permission already granted. Enable the SMS button.
            Log.d(TAG, "Permission Granted")
            /* Toast.makeText(
                this, "Permisos bien",
                Toast.LENGTH_LONG
            ).show() */

        }
    }


    /**
     * Processes permission request codes.
     *
     * @param requestCode  The request code passed in requestPermissions()
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        // For the requestCode, check if permission was granted or not.
        Log.d(TAG, "Verificando permiso")
        /* Toast.makeText(
            this, "Verificando permiso",
            Toast.LENGTH_SHORT
        ).show() */
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_RECEIVE_SMS -> {
                if (permissions[0].equals(Manifest.permission.RECEIVE_SMS, ignoreCase = true)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission was granted. Enable sms button.
                    /* Log.d(TAG, "Permisos Ok")
                    Toast.makeText(
                        this, "Permisos OK",
                        Toast.LENGTH_SHORT
                    ).show() */
                } else {
                    // Permission denied.
                    Log.d(TAG, "failure_permission****")
                    /* Toast.makeText(
                        this, "Falla de los permisos....",
                        Toast.LENGTH_SHORT
                    ).show() */
                    // Disable the sms button.

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity.main);
        Log.d(TAG, "Antes de Pedir Permiso")
        /* Toast.makeText(
            this, "Antes de Pedir Permiso",
            Toast.LENGTH_SHORT
        ).show() */
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECEIVE_SMS),
            MY_PERMISSIONS_REQUEST_RECEIVE_SMS
        )
        //DummyModel
        val requestModel = RequestModel("username123")
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.sendReq(requestModel).enqueue(
            object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    responsea: Response<ResponseModel>
                ) {

                    Log.d("HOLA", responsea.body().toString().toString())

                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.d(TAG, "HOLA " + TAG)
                    Log.d("HOLA", t.toString())
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
                }

            }
        )

//        checkForSmsPermission();

        setContent {
            EstoEsUnaPruebaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {

    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
    }

    Column() {
        Text(text = "Hello ${text.text}!")
        Button(onClick = {

        }) {
            Text(text = "Click me!")
        }
        OutlinedTextField(
            text,
            onValueChange = {
                text = it
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EstoEsUnaPruebaTheme {
        Greeting("Android")
    }
}