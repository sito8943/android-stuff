package com.example.estoesunaprueba

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast

class SMSSender {
    companion object {
        fun sendSMS (context: Context, content: String, target: String) {
            // on the below line we are creating a try and catch block
            try {

                // on below line we are initializing sms manager.
                //as after android 10 the getDefault function no longer works
                //so we have to check that if our android version is greater
                //than or equal to android version 6.0 i.e SDK 23
                val smsManager: SmsManager = if (Build.VERSION.SDK_INT>=23) {
                    //if SDK is greater that or equal to 23 then
                    //this is how we will initialize the SmsManager
                    context.getSystemService(SmsManager::class.java)
                } else{
                    //if user's SDK is less than 23 then
                    //SmsManager will be initialized like this
                    SmsManager.getDefault()
                }

                // on below line we are sending text message.
                smsManager.sendTextMessage(target, null, content, null, null)

                // on below line we are displaying a toast message for message send,

                Toast.makeText(context.applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {

                // on catch block we are displaying toast message for error.
                Toast.makeText(context.applicationContext, "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}