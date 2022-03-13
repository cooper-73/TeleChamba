package com.example.telechamba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_proyecto.view.*
import kotlinx.android.synthetic.main.item_proyecto_perfil.view.*

class ProyectoPerfilAdapter(private val mContext: Context, private val listaProyectos: List<ProyectoPerfil>) : ArrayAdapter<ProyectoPerfil>(mContext, 0,listaProyectos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_proyecto_perfil,parent,false)

        val Proyecto = listaProyectos[position]

        layout.nombreProyectoPerfil.text = Proyecto.nombre

        return layout
    }
}