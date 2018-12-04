package com.appsmaggys.caear.appfuentedesodamaggys.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosImagenes
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosPedidos
import com.appsmaggys.caear.appfuentedesodamaggys.RecyclerAdapter
import com.appsmaggys.caear.appfuentedesodamaggys.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_pedir.*

class PedirFragment : Fragment() {

    lateinit var referenciaImagenes1 : DatabaseReference
    lateinit var referenciaImagenes2 : DatabaseReference
    lateinit var referenciaImagenes3 : DatabaseReference
    lateinit var referenciaImagenes4 : DatabaseReference
    lateinit var referenciaImagenes5 : DatabaseReference
    lateinit var referenciaImagenes6 : DatabaseReference
    lateinit var referenciaImagenes7 : DatabaseReference
    lateinit var referenciaImagenes8 : DatabaseReference
    lateinit var referenciaPedidos : DatabaseReference
    lateinit var referenciaConfirmados : DatabaseReference

    lateinit var imagenList1:MutableList<DatosImagenes>
    lateinit var imagenList2:MutableList<DatosImagenes>
    lateinit var imagenList3:MutableList<DatosImagenes>
    lateinit var imagenList4:MutableList<DatosImagenes>
    lateinit var imagenList5:MutableList<DatosImagenes>
    lateinit var imagenList6:MutableList<DatosImagenes>
    lateinit var imagenList7:MutableList<DatosImagenes>
    lateinit var imagenList8:MutableList<DatosImagenes>

    lateinit var recyclerImagenes1: RecyclerView
    lateinit var recyclerImagenes2: RecyclerView
    lateinit var recyclerImagenes3: RecyclerView
    lateinit var recyclerImagenes4: RecyclerView
    lateinit var recyclerImagenes5: RecyclerView
    lateinit var recyclerImagenes6: RecyclerView
    lateinit var recyclerImagenes7: RecyclerView
    lateinit var recyclerImagenes8: RecyclerView

    lateinit var fab :FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab=fabPedir
        fab.setOnClickListener { view ->

            val frag2 = ButtonfloatingFragment()
            fragmentManager
                ?.beginTransaction()
                ?.add(R.id.contenedorFragments,frag2)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()

        }
        recyclerImagenes1=rv_menus1
        recyclerImagenes2=rv_menus2
        recyclerImagenes3=rv_menus3
        recyclerImagenes4=rv_menus4
        recyclerImagenes5=rv_menus5
        recyclerImagenes6=rv_menus6
        recyclerImagenes7=rv_menus7
        recyclerImagenes8=rv_menus8
        imagenList1= mutableListOf()
        imagenList2= mutableListOf()
        imagenList3= mutableListOf()
        imagenList4= mutableListOf()
        imagenList5= mutableListOf()
        imagenList6= mutableListOf()
        imagenList7= mutableListOf()
        imagenList8= mutableListOf()

        referenciaImagenes1 = FirebaseDatabase.getInstance().getReference("menus")
        referenciaImagenes2 = FirebaseDatabase.getInstance().getReference("combos")
        referenciaImagenes3 = FirebaseDatabase.getInstance().getReference("postres")
        referenciaImagenes4 = FirebaseDatabase.getInstance().getReference("comidarapida")
        referenciaImagenes5 = FirebaseDatabase.getInstance().getReference("platosalacarta")
        referenciaImagenes6 = FirebaseDatabase.getInstance().getReference("licuados")
        referenciaImagenes7 = FirebaseDatabase.getInstance().getReference("bebidas")
        referenciaImagenes8 = FirebaseDatabase.getInstance().getReference("sandwiches")


        referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")
        referenciaConfirmados = FirebaseDatabase.getInstance().getReference("Confirmados")

        recyclerImagenes1.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter1= RecyclerAdapter(imagenList1)
        recyclerImagenes1.adapter =miAdapter1

        recyclerImagenes2.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter2= RecyclerAdapter(imagenList2)
        recyclerImagenes2.adapter =miAdapter2

        recyclerImagenes3.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter3= RecyclerAdapter(imagenList3)
        recyclerImagenes3.adapter =miAdapter3

        recyclerImagenes4.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter4= RecyclerAdapter(imagenList4)
        recyclerImagenes4.adapter =miAdapter4

        recyclerImagenes5.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter5= RecyclerAdapter(imagenList5)
        recyclerImagenes5.adapter =miAdapter5

        recyclerImagenes6.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter6= RecyclerAdapter(imagenList6)
        recyclerImagenes6.adapter =miAdapter6

        recyclerImagenes7.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter7= RecyclerAdapter(imagenList7)
        recyclerImagenes7.adapter =miAdapter7

        recyclerImagenes8.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter8= RecyclerAdapter(imagenList8)
        recyclerImagenes8.adapter =miAdapter8



        referenciaImagenes1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList1.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList1.add(uplo!!)
                        recyclerImagenes1.adapter=miAdapter1
                    }
                }else{
                    imagenList1.clear()
                }
            }
        })
        referenciaImagenes2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList2.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList2.add(uplo!!)
                        recyclerImagenes2.adapter=miAdapter2
                    }
                }else{
                    imagenList2.clear()
                }
            }
        })

        referenciaImagenes3.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList3.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList3.add(uplo!!)
                        recyclerImagenes3.adapter=miAdapter3
                    }
                }else{
                    imagenList3.clear()
                }
            }
        })

        referenciaImagenes4.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList4.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList4.add(uplo!!)
                        recyclerImagenes4.adapter=miAdapter4
                    }
                }else{
                    imagenList4.clear()
                }
            }
        })

        referenciaImagenes5.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList5.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList5.add(uplo!!)
                        recyclerImagenes5.adapter=miAdapter5
                    }
                }else{
                    imagenList1.clear()
                }
            }
        })

        referenciaImagenes6.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList6.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList6.add(uplo!!)
                        recyclerImagenes6.adapter=miAdapter6
                    }
                }else{
                    imagenList6.clear()
                }
            }
        })

        referenciaImagenes7.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList7.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList7.add(uplo!!)
                        recyclerImagenes7.adapter=miAdapter7
                    }
                }else{
                    imagenList7.clear()
                }
            }
        })

        referenciaImagenes8.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList8.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(DatosImagenes::class.java)
                        imagenList8.add(uplo!!)
                        recyclerImagenes8.adapter=miAdapter8
                    }
                }else{
                    imagenList8.clear()
                }
            }
        })





//*******************************Pasamos todos los atributos de la base de datos a la lista de pedidos***********************************************
                /*referenciaPedidos.addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()){
                            precioTotal=0
                            pedidosList.clear()

                            for (h in p0.children){
                                val hero = h.getValue(DatosPedidos::class.java)
                                pedidosList.add(hero!!)
                            }
                            recyclerPedidos.adapter=mi2Adapter
                            for (h in pedidosList){
                                precioTotal = precioTotal + (h.cant.toInt() * h.precio.toInt())
                            }
                            precio.text=precioTotal.toString()
                        }else{
                            pedidosList.clear()
                            recyclerPedidos.adapter=mi2Adapter
                            precioTotal = 0
                            precio.text=precioTotal.toString()
                        }
                    }
                }
                )

            }*/
        }






    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_pedir, container, false)

       /* val btn =v.btnEnviar
        btn.setOnClickListener {

            for (h in pedidosList){
                val heroId =  FirebaseAuth.getInstance().uid.toString()
                val heros = referenciaPedidos.push().key.toString()
                val hero = DatosPedidos(heroId,"",h.menu,h.llevar,h.cant,h.precio,heros,"A confirmar","","","","","")
                referenciaConfirmados.child(heros).setValue(hero)
            }
                referenciaPedidos.removeValue()
                irFragment()

        }*/
    return v
    }

    /*private fun irFragment() {
        val frag2 = PedidosFragment()
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.contenedorFragments,frag2)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }*/

    companion object {
        fun newInstance(): PedirFragment{
            val fragment=PedirFragment()
            return fragment
        }
    }
}

