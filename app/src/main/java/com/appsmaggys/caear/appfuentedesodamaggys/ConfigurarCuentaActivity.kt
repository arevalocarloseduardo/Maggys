package com.appsmaggys.caear.appfuentedesodamaggys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosUsuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_configurar_cuenta.*


class ConfigurarCuentaActivity : AppCompatActivity() {

    lateinit var txtTelefono1: EditText
    lateinit var txtDireccion: EditText
    lateinit var referenciaUsuarios : DatabaseReference
    lateinit var usuarioLista1:MutableList<DatosUsuario>
    lateinit var progress:ProgressBar
    lateinit var auth: FirebaseAuth
    lateinit var btnListo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configurar_cuenta)
        progress=progressBarLog
        txtTelefono1= tiTelefono
        txtDireccion = tiDireccion
        btnListo = btn_Listo
        usuarioLista1=mutableListOf()
        btnListo.visibility=View.VISIBLE
        referenciaUsuarios = FirebaseDatabase.getInstance().getReference("Users")
        traerDatos(referenciaUsuarios)
        btnListo.setOnClickListener {
            enviarInfo()
        }
    }

    private fun enviarInfo() {
        val telef:String=txtTelefono1.text.toString()
        val direcc:String=txtDireccion.text.toString()


        if(!TextUtils.isEmpty(telef)&&!TextUtils.isEmpty(direcc)){

            progressBarLog.visibility=View.VISIBLE
            btnListo.visibility=View.GONE
                val uid = FirebaseAuth.getInstance().uid ?:""//guardo en uid la autentificacion
                val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")//carlos= $uid creo una base de datos re piola
                for (h in usuarioLista1){
                    val user = DatosUsuario(uid,h.nombre,h.fotoPerfil,h.edad,h.correo,telef,h.puntos,h.lvl,direcc)//guardo la imagen y
                    ref.setValue(user).addOnSuccessListener {
                        Toast.makeText(this,"Cambios efectuados correctamente", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MenuActivity::class.java))
                    }.addOnFailureListener {
                        progressBarLog.visibility=View.GONE
                        btnListo.visibility=View.VISIBLE
                        Toast.makeText(this,"hubo un problema",Toast.LENGTH_LONG).show() }
                    startActivity(Intent(this,MenuActivity::class.java))
                }

        }else{
            progressBarLog.visibility=View.GONE
            btnListo.visibility=View.VISIBLE
            Toast.makeText(this,"Hubo un problema",Toast.LENGTH_LONG).show()}
    }

        fun traerDatos(referencia: DatabaseReference) {
            referencia.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        for (h in p0.children)
                        {
                            val uid = FirebaseAuth.getInstance().uid
                            val idClienteRegistrado = h.getValue(DatosUsuario::class.java)?.uid
                            val hero = h.getValue(DatosUsuario::class.java)
                            if (uid==idClienteRegistrado){
                                usuarioLista1.add(hero!!)
                            }

                        }
                    }
                }
            })
    }

}
