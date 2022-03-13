package com.example.telechamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val name: TextView = findViewById(R.id.mostrarNombre)
        val lastName: TextView = findViewById(R.id.mostrarApellido)

        name.text = Usuario.nombre
        lastName.text = Usuario.apellido

        listaProyectoPerfil(Usuario.username)

    }
/*
    private fun listaProyectoPerfil() {
        val proyecto = Proyecto("testing654321", "descripcionTextual")
        val proyecto2 = Proyecto("polleria", "pollitoalabrasa")

        val listProyectosPerfil = listOf(proyecto,proyecto2)

        val adapter = ProyectoPerfilAdapter(this, listProyectosPerfil)

        listProyectoPerfil.adapter = adapter

        listProyectoPerfil.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, ProjectOwnerView::class.java)
            intent.putExtra("proyecto",listProyectosPerfil[i])
            startActivity(intent)

        }
    }
*/
    private fun listaProyectoPerfil(email_user: String){
    // consulta http para llenar el main #######################################
    //val url = "http://telechambac19.herokuapp.com/adminproyectos?creador="+email_user
    //val url = "http://telechambac19.herokuapp.com/adminproyectos?creador=h4x0r@web.onion"
    val url = "https://telechambac19.herokuapp.com/findproyect"

    url.httpGet()
        .responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    Toast.makeText(this, "Error al cargar trabajos", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val gson = Gson()
                    var resp = gson.fromJson(result.value, Array<TrabajoClass>::class.java)

                    //var listResp = resp.toList()
                    val listProyectosPerfil: MutableList<ProyectoPerfil> = mutableListOf()

                    //Log.d("Tag 0", listResp[0].nombre)

                    resp.forEach {
                        if(Usuario.username == it.creador){
                            listProyectosPerfil.add(
                                ProyectoPerfil(
                                    it.nombre.toString()
                                )
                            )

                        }

                    }
                    /*for(i in 0 .. resp.size) {
                        val temporal = Proyecto(resp[i].nombre, resp[i].descripcion)
                        listProyectos.add(temporal)
                    }*/

                    val adapter = ProyectoPerfilAdapter(this, listProyectosPerfil)

                    listProyectoPerfil.adapter = adapter

                    listProyectoPerfil.setOnItemClickListener { adapterView, view, i, l ->
                        val intent = Intent(this, ProjectOwnerView::class.java)
                        intent.putExtra("empresa",listProyectosPerfil[i].nombre)
                        startActivity(intent)

                    }

                }
            }
        }


}
    
    
    fun onClick(p0: View?) {
        val regresar = Intent(this , MainActivity::class.java)
        startActivity(regresar)
    }
}