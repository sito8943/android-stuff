package com.example.estoesunaprueba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {
    private static String SMS = "android.provider.Telephony.SMS_RECEIVED";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Hola", "HOla");
        Toast.makeText(context, "OnReceive", Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals(SMS)) {
            Bundle bundle = intent.getExtras();

            Object[] objects = (Object[]) bundle.get("pdus");

            SmsMessage[] messages = new SmsMessage[objects.length];
            for (int i = 0; i < objects.length; i += 1) {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }

            Toast.makeText(context, messages[0].getMessageBody(), Toast.LENGTH_SHORT).show();
        }

    }
}
