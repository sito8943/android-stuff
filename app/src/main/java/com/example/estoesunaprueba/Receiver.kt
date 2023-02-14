import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.estoesunaprueba.MainActivity


class SmsReceiver : BroadcastReceiver() {
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent) {
        val intentExtras = intent.extras
        if (intentExtras != null) {
            val sms = intentExtras[SMS_BUNDLE] as Array<Any>?
            var smsMessageStr = ""
            for (i in sms!!.indices) {
                val smsMessage: SmsMessage = SmsMessage.createFromPdu(sms[i] as ByteArray)
                val smsBody: String = smsMessage.getMessageBody().toString()
                val address: String = smsMessage.getOriginatingAddress().toString()
                smsMessageStr += "SMS From: $address\n"
                smsMessageStr += """
                    $smsBody
                    
                    """.trimIndent()
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        const val SMS_BUNDLE = "pdus"
    }
}