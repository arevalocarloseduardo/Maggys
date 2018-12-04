package com.appsmaggys.caear.appfuentedesodamaggys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Log
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosUsuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class RegisterWhiteFacebook : AppCompatActivity() {
    lateinit var loginButton:LoginButton
    lateinit var callBackManager: CallbackManager
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var text:TextView
    lateinit var dbRefernce: DatabaseReference
    lateinit var database:FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_white_facebook)

        try {
            val info = packageManager.getPackageInfo(
                "com.appmaggys.caear.appfuentedesodamaggys",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

        database= FirebaseDatabase.getInstance()
        dbRefernce=database.reference.child("User")


        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intenti = Intent(this, IntroActivity::class.java)
            // intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intenti)
        }else{val intenti2 = Intent(this, MenuActivity::class.java)
            // intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intenti2)}

       // text = txtIniciar

       // FacebookSdk.sdkInitialize(applicationContext)
       // AppEventsLogger.activateApp(this)
        auth= FirebaseAuth.getInstance()
        loginButton=login_button
        callBackManager= CallbackManager.Factory.create()
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"))
        val card=cardFace
        val progressBar=progressBarface

        loginButton.setOnClickListener {
            card.visibility=View.GONE
            progressBar.visibility=View.VISIBLE

            LoginManager.getInstance().registerCallback(callBackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                        card.visibility=View.VISIBLE
                        val intentio2 = Intent(applicationContext, MenuActivity::class.java)
                        intentio2.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intentio2)
                        traerToken(result?.accessToken)

                        card.visibility=View.GONE
                    finish()

                }
                override fun onCancel() {
                    card.visibility=View.VISIBLE
                    progressBar.visibility=View.GONE
                    Toast.makeText(applicationContext,"error cancelado",Toast.LENGTH_LONG).show()
                }

                override fun onError(error: FacebookException?) {
                    card.visibility=View.VISIBLE
                    progressBar.visibility=View.GONE
                    Toast.makeText(applicationContext,"error conecction",Toast.LENGTH_LONG).show()                }
            }
            )
        }


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
        /*
        val nombre = myuserobj?.displayName.toString()
        val imagen = myuserobj?.photoUrl.toString()
        val telefono = myuserobj?.phoneNumber.toString()
        val correo = myuserobj?.email.toString()
        val uid = auth.uid //guardo en uid la autentificacion
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")//carlos= $uid creo una base de datos re piola
        val user = DatosUsuario(uid!!,nombre,imagen,"edad",correo,telefono,"0","1","Ninguna")//guardo la imagen y
        ref.setValue(user)
*/
    }

}


