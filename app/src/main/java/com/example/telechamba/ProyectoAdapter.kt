package com.example.telechamba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_proyecto.view.*

class ProyectoAdapter(private val mContext: Context, private val listaProyectos: List<Proyecto>) : ArrayAdapter<Proyecto>(mContext, 0,listaProyectos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_proyecto,parent,false)

        val Proyecto = listaProyectos[position]

        layout.nombreProyecto.text = Proyecto.nombre
        layout.Descripcion.text = Proyecto.descripcion

        return layout
    }
}