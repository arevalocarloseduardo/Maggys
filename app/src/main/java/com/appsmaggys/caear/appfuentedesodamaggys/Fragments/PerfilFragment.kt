package com.appsmaggys.caear.appfuentedesodamaggys.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.ConfigurarCuentaActivity
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosUsuario
import com.appsmaggys.caear.appfuentedesodamaggys.RegisterActivity
import com.appsmaggys.caear.appfuentedesodamaggys.SubirImagen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*


class PerfilFragment : Fragment() {

    lateinit var referenciaUsuarios : DatabaseReference
    lateinit var usuarioLista:MutableList<DatosUsuario>
    lateinit var puntos : TextView
    lateinit var nombre :TextView
    lateinit var correo :TextView
    lateinit var telefono :TextView
    lateinit var direccion :TextView
    lateinit var nombreFc:TextView
    lateinit var imagfc:ImageView
    lateinit var btnAgre:Button

    lateinit var btnConfigurar: Button
    var hayDatos:Boolean = false

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_perfil, container, false)

        auth=FirebaseAuth.getInstance()
        var myuserobj = auth.currentUser

        val nombre = myuserobj?.displayName.toString()
        val imagen = myuserobj?.photoUrl.toString()
        val telefonos = myuserobj?.phoneNumber.toString()
        val correo = myuserobj?.email.toString()
        nombreFc = v.txtNombrefc
        imagfc=v.imgFacebook

        val imageView=myuserobj?.photoUrl
        Picasso.get().load(imageView).into(imagfc)
        nombreFc?.text= myuserobj?.displayName



    referenciaUsuarios = FirebaseDatabase.getInstance().getReference("Users")
    usuarioLista = mutableListOf()
    puntos = v.tvPerfilPuntos
    telefono = v.tvPerfilTelefono
    direccion = v.tvPerfilDireccion
    btnConfigurar = v.btnPerfilConfigurar
        btnAgre=v.btnAgregarPhoto
    verPuntos(referenciaUsuarios)


        btnAgre.setOnClickListener {  startActivity(Intent(activity, SubirImagen::class.java))}

    btnConfigurar.setOnClickListener { enviarFragment() }

return v
}

private fun enviarFragment() {
startActivity(Intent(activity, ConfigurarCuentaActivity::class.java))
}

    private fun verPuntos(referencia: DatabaseReference) {
        referencia.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (h in p0.children)
                    {
                        val uid = FirebaseAuth.getInstance().uid
                        val idClienteRegistrado = h.getValue(DatosUsuario::class.java)?.uid
                        if (uid==idClienteRegistrado){
                            hayDatos =true
                        }
                    }
                    if (hayDatos){
                        for (h in p0.children) {
                            val uid = FirebaseAuth.getInstance().uid
                            val idClienteRegistrado = h.getValue(DatosUsuario::class.java)?.uid
                            val hero = h.getValue(DatosUsuario::class.java)
                            if (uid == idClienteRegistrado) {
                                usuarioLista.add(hero!!)
                            }
                        }
                    }else{
                        var myuserobj = auth.currentUser
                        val nombre = myuserobj?.displayName.toString()
                        val imagen = myuserobj?.photoUrl.toString()
                        val telefono = myuserobj?.phoneNumber.toString()
                        val correo = myuserobj?.email.toString()
                        val uid = auth.uid //guardo en uid la autentificacion
                        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")//carlos= $uid creo una base de datos re piola
                        val user = DatosUsuario(uid!!,nombre,imagen,"",correo,telefono,"0","","")//guardo la imagen y
                        ref.setValue(user)
                        usuarioLista.add(user)
                    }
                    for (h in usuarioLista)
                    {
                        puntos.text=h.puntos
                        telefono.text=h.telefono
                        direccion.text=h.direccion
                    }
                }
                else
                {
                    val uid = auth.uid //guardo en uid la autentificacion
                    val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")//carlos= $uid creo una base de datos re piola
                    val user = DatosUsuario(uid!!,"","","edad","","","","1","Ninguna")//guardo la imagen y
                    ref.setValue(user)
                }
            }
        })


    }
private fun verifivarAuth() {
val uid = FirebaseAuth.getInstance().uid
if(uid==null){
    val intent = Intent(activity, RegisterActivity::class.java)
    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}
}
companion object {
fun newInstance(): PerfilFragment{
val fragment=PerfilFragment()
return fragment
}
}
}
