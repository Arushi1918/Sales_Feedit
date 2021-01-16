package com.project.feedit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_signup.setOnClickListener {
            var i=Intent(this,register::class.java)
            startActivity(i)
        }

        login_btn.setOnClickListener {
            var url = "http://192.168.29.155/Salesweb/login.php?mobile="+login_mobile.text.toString()+ "&password=" + login_password.text.toString()
            var req: RequestQueue = Volley.newRequestQueue(this)
            var sr= StringRequest(Request.Method.GET, url, Response.Listener { response->
                if(response.equals("0"))
                    Toast.makeText(this,"Login Failed", Toast.LENGTH_LONG).show()
                else {
                    UserInfo.mobile=login_mobile.text.toString()
                    UserInfo.access_token=response;
                    var i = Intent(this, HomeAct::class.java)
                    startActivity(i)
                }
            }, Response.ErrorListener { error->
                Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()

            })

            req.add(sr)
        }

    }
}
