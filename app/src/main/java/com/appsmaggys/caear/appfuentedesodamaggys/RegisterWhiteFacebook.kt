package com.appsmaggys.caear.appfuentedesodamaggys

import android.content.Intent
import android.os.Bundle
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_register_white_facebook.*
import java.util.*


class RegisterWhiteFacebook : AppCompatActivity() {
    lateinit var loginButton:LoginButton
    lateinit var callBackManager: CallbackManager
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var text:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_white_facebook)

        text = textView6

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        auth= FirebaseAuth.getInstance()
        loginButton=login_button
        callBackManager= CallbackManager.Factory.create()
        loginButton.setReadPermissions(Arrays.asList("email"))

        loginButton.setOnClickListener {
            LoginManager.getInstance().registerCallback(callBackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                        traerToken(result?.accessToken)                }

                override fun onCancel() {
                    Toast.makeText(applicationContext,"error cancelado",Toast.LENGTH_LONG).show()            }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(applicationContext,"error conecction",Toast.LENGTH_LONG).show()                }
            }        ) }
        }

    private fun traerToken(accessToken: AccessToken?) {
        var credential = FacebookAuthProvider.getCredential(accessToken!!.token)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){
                task ->
            if (task.isSuccessful){
                var myuserobj = auth.currentUser
                updateUI(myuserobj)
            }else{

                Toast.makeText(this,"error en la autentificacion",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateUI(myuserobj: FirebaseUser?) {
       text.text= myuserobj?.email

    }
}

