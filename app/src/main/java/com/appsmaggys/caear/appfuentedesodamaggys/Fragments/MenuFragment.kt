package com.appsmaggys.caear.appfuentedesodamaggys.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosMenuPrincipal
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.AdaptadoresFragments.MenuPrincipalAdapter
import com.appsmaggys.caear.appfuentedesodamaggys.RecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_buttonfloating.*
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment() {
    lateinit var referenciaImagenes : DatabaseReference
    lateinit var imagenList:MutableList<DatosMenuPrincipal>
    lateinit var recyclerImagenes: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerImagenes=rv_menuFragment
        imagenList= mutableListOf()
        referenciaImagenes = FirebaseDatabase.getInstance().getReference("menuprincipal")

        recyclerImagenes.layoutManager= LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
        val miAdapter= MenuPrincipalAdapter(imagenList)
        recyclerImagenes.adapter =miAdapter

        referenciaImagenes.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        imagenList.clear()
                        if(p0.exists()){
                            for (h in p0.children){
                                val uplo = h.getValue(DatosMenuPrincipal::class.java)
                                imagenList.add(uplo!!)
                                recyclerImagenes.adapter=miAdapter
                            }
                        }else{
                           imagenList.clear()
                        }
                    }
                })

            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_menu, container, false)
        return v
    }

    companion object {
        fun newInstance(): MenuFragment{
            val fragment=MenuFragment()
            return fragment
        }
    }
}
