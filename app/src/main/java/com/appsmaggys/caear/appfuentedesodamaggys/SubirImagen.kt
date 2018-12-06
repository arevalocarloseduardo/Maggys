package com.appsmaggys.caear.appfuentedesodamaggys

import android.app.Activity
import android.content.Intent

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.*
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosImagenes
import kotlinx.android.synthetic.main.activity_subir_imagen.*
import java.util.*

class SubirImagen : AppCompatActivity() {
    lateinit var mButtonChooseImage :Button
    lateinit var mButtonUpload :Button
    lateinit var mBar: ProgressBar
    lateinit var mImagen: ImageView
    lateinit var mEditText: EditText
    lateinit var mCate: EditText
    lateinit var mNombr: EditText
    lateinit var mPrec: EditText
    lateinit var mDesc: EditText
    lateinit var mTextView: TextView
    lateinit var mStorage:StorageReference
    lateinit var mDatabaseReference: DatabaseReference


    var selectedPhotoUrl :Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subir_imagen)

        mStorage=FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseReference=FirebaseDatabase.getInstance().getReference("uploads")

        mButtonChooseImage=mbutton
        mButtonUpload=mbutton2
        mBar=mprogressBar
        mImagen=mimageView
        mEditText=meditText
        mTextView=mtextView
        mCate=edtCategory
        mNombr=edtNombrePro
        mPrec=edtPrecio
        mDesc=edtDesc

        mButtonUpload.setOnClickListener {
            subirImagenAFirebase()
        }
      mButtonChooseImage.setOnClickListener {
          OpenfileChooser()
      }
    }

    private fun subirImagenAFirebase() {

        if (selectedPhotoUrl==null)return//si no hay imagen seleccionada que salga de la funcion
        val filename = UUID.randomUUID().toString()// guardo una variable con un nombre ramdom
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")//indico donde guardar la imagen juntamente con el nombre ramdom
        ref.putFile(selectedPhotoUrl!!)
            .addOnSuccessListener {
                //y guarda la imagen en la direccion donde le indique
                Log.d("tag", "yape:${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("tag", "yase:$it")
                   val categor= mCate.text.toString()
                    val nombreP= mNombr.text.toString()
                    val prec= mPrec.text.toString()
                    val descri= mDesc.text.toString()

                salvarUsuarioFirebase(it.toString(),categor,nombreP,prec,descri)
                    Picasso.get().load(it.toString()).into(mImagen)
            }}
               .addOnFailureListener {//obtengo la direccion de la imagen subida a firebase y se la mando a la funcion
               }
    }

    private fun salvarUsuarioFirebase(imagenUrl: String,categoria: String,nombreProducto: String,precio: String,descripcion: String
    ) //recibo la direccion de la imagen
    {

        val ref = FirebaseDatabase.getInstance().getReference("/$categoria/$nombreProducto")//carlos= $uid creo una base de datos re piola
        val user = DatosImagenes("Carlos", nombreProducto, imagenUrl, precio, descripcion)//guardo la imagen y
        ref.setValue(user)
    }

    private fun OpenfileChooser() {
        //esta funcion sirve para abrir la galeria y seleccionar una foto
        val intent = Intent(Intent.ACTION_PICK)//ACTION_GET_CONTEN
        intent.type = "image/*"
        startActivityForResult(intent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode == Activity.RESULT_OK && data !=null){
            //se selecciona una imagen de la galeria

           // intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)// con esto se cierra todas las activitys anteriores

            selectedPhotoUrl = data.data
            //se guarda el dato en selecedPhotoUrl
                //val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUrl)
                //val bitmapDrawable = BitmapDrawable(bitmap)
                //mbutton.setBackgroundDrawable(bitmapDrawable)
        }
    }

}
