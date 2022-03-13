package com.example.telechamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llenarDatos(Usuario.username)

        listaProyecto()
    }

    private fun llenarDatos(email_user: String) {
        val enlace = "https://telechambac19.herokuapp.com/getusers?usr="+email_user
        enlace.httpGet()
            .responseString{request, response, result ->
                when(result) {
                    is Result.Failure -> {
                        Toast.makeText(this, "Error funcional", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        val gson = Gson()
                        val resp = gson.fromJson(result.value, Array<User>::class.java)
                        Usuario.nombre = resp[0].nombre
                        Usuario.apellido = resp[0].apellido
                        Usuario.username = resp[0].username

                    }
                }
            }
    }

    private fun listaProyecto() {

        // consulta http para llenar el main #######################################
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
                        val listProyectos: MutableList<Proyecto> = mutableListOf()

                        resp.forEach {
                            listProyectos.add(
                                Proyecto(
                                    it.nombre.toString(),
                                    it.descripcion.toString(),
                                    it.creador.toString(),
                                    it.vacantes.toString(),
                                    it.sueldo.toString(),
                                    it.rubro.toString()
                                )
                            )
                        }


                        /*for(i in 0 .. resp.size) {
                            val temporal = Proyecto(resp[i].nombre, resp[i].descripcion)
                            listProyectos.add(temporal)
                        }*/

                        val adapter = ProyectoAdapter(this, listProyectos)


                        listProyecto.adapter = adapter

                        listProyecto.setOnItemClickListener { adapterView, view, i, l ->
                            val intentOwner = Intent(this, ProjectOwnerView::class.java)
                            val intentUser = Intent(this, ProjectUserView::class.java)
                            //Enviando los valor nombre de empresa, descripcion
                            intentOwner.putExtra("empresa", listProyectos[i].nombre)
                            intentOwner.putExtra("descripcion", listProyectos[i].descripcion)
                            //Enviando los valor nombre de empresa, descripcion, vacantes, sueldo, rubro
                            intentUser.putExtra("nombre",listProyectos[i].nombre)
                            intentUser.putExtra("descripcion",listProyectos[i].descripcion)
                            intentUser.putExtra("vacantes",listProyectos[i].vacantes)
                            intentUser.putExtra("sueldo",listProyectos[i].sueldo)
                            intentUser.putExtra("rubro",listProyectos[i].rubro)


                            if(Usuario.username == listProyectos[i].creador) {
                                startActivity(intentOwner)
                            }
                            else
                                startActivity(intentUser)
                        }

                    }
                }
            }
    }
    // consulta http para llenar el main #######################################

    fun onClick(p0: View) {

        when (p0.id){
            R.id.btnPerfil ->{
                val perfil = Intent(this , Profile::class.java)
                startActivity(perfil)

            }
            R.id.btnCrearProyecto ->{

                val crearProyecto = Intent(this ,CreateProjectActivity::class.java )
                startActivity(crearProyecto)

            }
            R.id.btnSalir ->{
                val salir = Intent(Intent.ACTION_MAIN)
                salir.addCategory(Intent.CATEGORY_HOME)
                salir.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(salir)
            }
            else -> Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_LONG).show()

        }
    }


}