package com.appsmaggys.caear.appfuentedesodamaggys.Fragments.AdaptadoresFragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosMenuPrincipal
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menu_principal.view.*


class MenuPrincipalAdapter(var list: MutableList<DatosMenuPrincipal>): RecyclerView.Adapter<MenuPrincipalAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menu_principal,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: MenuPrincipalAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: DatosMenuPrincipal){

            val text = data.mimageUrl
            val progresBar=itemView.progressBarMenuPrincipal

            progresBar.visibility = View.VISIBLE
            Picasso.get().load(text).into(itemView.imagenMenuPrincipal,
                object : com.squareup.picasso.Callback {
                    override fun onError(e: Exception?) {
                    }
                    override fun onSuccess() {
                       progresBar.visibility = View.GONE
                    }
                })

        }
    }
}
