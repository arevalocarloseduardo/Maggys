package com.appsmaggys.caear.appfuentedesodamaggys.Fragments


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosPedidos
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.AdaptadoresFragments.AdapterFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_buttonfloating.*
import kotlinx.android.synthetic.main.fragment_buttonfloating.view.*

class ButtonfloatingFragment : Fragment() {
    lateinit var recyclerPedidos:RecyclerView
    lateinit var pedidosList:MutableList<DatosPedidos>
    lateinit var referenciaPedidos : DatabaseReference
    lateinit var referenciaConfirmados : DatabaseReference
    lateinit var fab : FloatingActionButton
    lateinit var btn : Button
    var precioTotal=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_buttonfloating, container, false)

        referenciaConfirmados = FirebaseDatabase.getInstance().getReference("Confirmados")

        btn =v.btnEnviarfloat
        btn.setOnClickListener {
            for (h in pedidosList){
                val heroId =  FirebaseAuth.getInstance().uid.toString()
                val heros = referenciaPedidos.push().key.toString()
                val hero = DatosPedidos(heroId,"",h.menu,h.llevar,h.cant,h.precio,heros,"A confirmar","","","","","")
                referenciaConfirmados.child(heros).setValue(hero)
            }
                referenciaPedidos.removeValue()
                irFragment()

    }
        return v
    }

    private fun irFragment() {
        val frag2 = PedidosFragment()
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.contenedorFragments,frag2)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerPedidos=recyclerPedidi
        pedidosList= mutableListOf()



        recyclerPedidos.layoutManager= LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
        val mi2Adapter= AdapterFragment(pedidosList)
        recyclerPedidos.adapter =mi2Adapter

        referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")

        fab=fabSal
        fab.setOnClickListener { view ->

            val frag2 = ButtonfloatingFragment()
            fragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

        referenciaPedidos.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val precio = txtPrecioFloat
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
                   precio?.text=precioTotal.toString()
                }else{
                    pedidosList.clear()
                    recyclerPedidos.adapter=mi2Adapter
                    precioTotal = 0
                    precio?.text=precioTotal.toString()
                }
            }
        }
        )
    }

    companion object {
        fun newInstance(): ButtonfloatingFragment{
            val fragment=ButtonfloatingFragment()
            return fragment
        }
    }
}
