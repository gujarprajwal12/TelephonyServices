package gujarprawjal12gmail.com.example.telephonyservices

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CellLocation
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sms()

    }

    private fun sms() {
        var intentFilter = IntentFilter("prajwal.sms.SENT")
        intentFilter.addAction("prajwal.sms.DELIVERED")
        registerReceiver(
            SMSBR(),
            intentFilter
        )

        var smsManager = SmsManager.getDefault()
        var sentIntent = PendingIntent.getBroadcast(
            this, 1, Intent("prajwal.sms.SENT"), 0
        )

        var deliveredIntent = PendingIntent.getBroadcast(
            this, 2, Intent("Prajwal.sms.DELIVERED"), 0
        )

        smsManager.sendTextMessage(
            "8999326370", "JIO", "You have done the telephoney code corret",
            sentIntent, deliveredIntent
        )

    }


    inner  class SMSBR : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
           mt("SMS: ${intent?.action}")
        }
    }


    @SuppressLint("MissingPermission", "NewApi")
    private fun telephony() {

        var teleManager : TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        mt("Phone type: ${teleManager.phoneType}")
        mt("Call State: ${teleManager.callState}")
        mt("Data State: ${teleManager.dataState}")
        mt("Data Activity: ${teleManager.dataActivity}")
        mt("Data Network Type: ${teleManager.dataNetworkType}")
        mt("IMEI: ${teleManager.getImei(1)}")
        mt("Network County ISO: ${teleManager.networkCountryIso}")
        mt("Sim INfo: ${teleManager.simOperator} ${teleManager.simState}  ${teleManager.simCountryIso}")


        mt("----------------------------------")

        var phoneStateListener = MyTeleListener()

        teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_DATA_ACTIVITY)
        teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CELL_LOCATION)
        teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE)

    }

    inner class MyTeleListener : PhoneStateListener() {

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            mt("Call State: $phoneNumber $state")
        }

        override fun onDataActivity(direction: Int) {
            super.onDataActivity(direction)
            mt("Data Activity: $direction")

        }

        override fun onCellLocationChanged(location: CellLocation?) {
            super.onCellLocationChanged(location)
            mt("Cell location: ${location.toString()}")
        }

        override fun onDataConnectionStateChanged(state: Int) {
            super.onDataConnectionStateChanged(state)
            mt("Data connection state: ${state}")
        }

    }


    private fun mt(text : String) {
        Log.e("tag", text)
    }
}
