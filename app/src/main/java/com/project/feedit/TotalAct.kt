package com.project.feedit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_total.*
import org.json.JSONException
import java.math.BigDecimal

class TotalAct : AppCompatActivity() {

    var config:PayPalConfiguration?=null
    var amount:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        var z="http://192.168.29.155/Salesweb/get_total.php?bill_no="+ intent.getStringExtra("bno")

        var rq: RequestQueue = Volley.newRequestQueue(this)
        var sr= StringRequest(Request.Method.GET,z, Response.Listener { response->

            total_tv.text="$"+response
            amount = response.toDouble()

        }, Response.ErrorListener { error->
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        })

        rq.add(sr)

        var paypalConfig: PayPalConfiguration = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(UserInfo.client_id)

        var ppService = Intent(this@TotalAct, PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(ppService)

        paypal_btn.setOnClickListener {

            var ppProcessing = PayPalPayment(BigDecimal.valueOf(amount), "USD", "Online Store ", PayPalPayment.PAYMENT_INTENT_SALE)

            var paypalPaymentIntent = Intent(this, PaymentActivity::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, ppProcessing)
            startActivityForResult(paypalPaymentIntent, 1000)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000){


                if (resultCode == Activity.RESULT_OK) {

                    var intent = Intent(this, ConfirmAct::class.java)
                    startActivity(intent)

                } else {

                    Toast.makeText(this, "Sorry Something went Wrong", Toast.LENGTH_LONG).show()
                }

            }



    }
    //for block to leaking cuz of paypal, u need to destroy end of to activity
    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, PayPalService::class.java))

    }
}
