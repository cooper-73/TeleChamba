package com.example.telechamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class ProjectOwnerView : AppCompatActivity() {
    //Lista de postulantes seleccionados
    var nombreEmpresa: String = ""
    var itemsPostulantes: Array<String> = emptyArray()
    var seleccionPostulantes: ArrayList<String> = arrayListOf<String>()

    //Trabajadores
    var seleccionTrabajadores: ArrayList<String> = arrayListOf<String>()
    var itemsTrabajadores:Array<String> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Syncr
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_owner_view2)

        //Recibir la variable nombreEmpresa de la activity Main
        val bundle = intent.extras
        nombreEmpresa = bundle?.getString("empresa").toString()
        val descripcion = bundle?.getString("descripcion").toString()

        val tNombreEmpresa: TextView = findViewById(R.id.textViewNombreOwner)
        val tDescripcion: TextView = findViewById(R.id.textViewDescripcionOwner)

        tNombreEmpresa.text = nombreEmpresa
        tDescripcion.text = descripcion

        //checkBox de Trabajadores
        val ch1: ListView = findViewById(R.id.checkable_list_trabajadores)
        val btnSeleccionarTrabajadores: Button = findViewById(R.id.btnDespedirTrabajador)
        listaProyectoTrabajador(nombreEmpresa)
        var adapterTrabajadores: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.row_layout, R.id.txt_lan, itemsTrabajadores)


        //checkBox de Postulantes
        val ch2: ListView = findViewById(R.id.checkable_list_postulantes)
        val btnSeleccionarPostulantes: Button = findViewById(R.id.btnSeleccionarPostulante)

        listaProyectoPostulantes(nombreEmpresa)

        println("Tamaño 3 : " + itemsPostulantes.size)

        val adapterPostulantes: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.row_layout, R.id.txt_lan, itemsPostulantes)

        setListView(ch1, adapterTrabajadores)
        setListView(ch2, adapterPostulantes)

        ch1.setOnItemClickListener { parent, view, position, id ->
            val seleccionado = adapterTrabajadores.getItem(position).toString()
            if(seleccionTrabajadores.contains(seleccionado)) {
                seleccionTrabajadores.remove(seleccionado)
            }
            else {
                seleccionTrabajadores.add(seleccionado)
            }
        }

        ch2.setOnItemClickListener { parent, view, position, id ->
            val seleccionado = adapterPostulantes.getItem(position).toString()
            if(seleccionPostulantes.contains(seleccionado)) {
                seleccionPostulantes.remove(seleccionado)
            }
            else {
                seleccionPostulantes.add(seleccionado)
            }
        }
        btnSeleccionarTrabajadores.setOnClickListener {
            if(seleccionTrabajadores.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No hay trabajadores seleccionados", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, Despedir::class.java)
                intent.putExtra("Seleccionados", seleccionTrabajadores)//
                intent.putExtra("nombreProyecto", nombreEmpresa)
                startActivity(intent)
            }
        }
        btnSeleccionarPostulantes.setOnClickListener {
            if(seleccionPostulantes.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No hay postulantes seleccionados", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, Contratar::class.java)
                intent.putExtra("Seleccionados", seleccionPostulantes)//
                intent.putExtra("NombreProyecto", nombreEmpresa)
                startActivity(intent)
            }
        }
    }

    fun setListView(ch1: ListView, adapter: ArrayAdapter<String>) {
        ch1.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        ch1.adapter = adapter
    }

    private fun listaProyectoTrabajador(nombreProyecto: String?) {

        // consulta http para llenar el main #######################################
        val url = "https://telechambac19.herokuapp.com/findproyect?nombre="+nombreProyecto

        var trabajadoresArray:Array<String> = emptyArray()

        val (request, response, result) = Fuel.get(url).responseString()

        when (result) {

            is Result.Failure -> {
                Toast.makeText(this, "Error al cargar trabajos", Toast.LENGTH_SHORT).show()
            }
            is Result.Success -> {
                Toast.makeText(getApplicationContext(), "Entre!!!!!", Toast.LENGTH_LONG).show()
                val gson = Gson()
                var resp = gson.fromJson(result.value, Array<TrabajoClass>::class.java)


                if(resp[0].contratados != null){
                    var trabajadoresList: MutableList<String> = mutableListOf()
                    resp[0].contratados.forEach {
                        trabajadoresList.add(it)
                    }
                    trabajadoresArray = trabajadoresList.toTypedArray()
                    println("Tamaño 0" + trabajadoresArray.size)
                    itemsTrabajadores = trabajadoresArray

                    itemsTrabajadores.forEach {
                        println(it)
                    }
                }

                println(resp[0].contratados)
            }

        }
    }
    private fun listaProyectoPostulantes(nombreProyecto: String?) {

        // consulta http para llenar el main #######################################
        val url = "https://telechambac19.herokuapp.com/findproyect?nombre="+nombreProyecto

        var postulantesArray:Array<String> = emptyArray()
        val (request, response, result) = Fuel.get(url).responseString()

        when (result) {

            is Result.Failure -> {
                Toast.makeText(this, "Error al cargar trabajos", Toast.LENGTH_SHORT).show()
            }
            is Result.Success -> {
                Toast.makeText(getApplicationContext(), "Entre!!!!!", Toast.LENGTH_LONG).show()
                val gson = Gson()
                var resp = gson.fromJson(result.value, Array<TrabajoClass>::class.java)


                if(resp[0].postulantes != null){
                    var postulantesList: MutableList<String> = mutableListOf()
                    resp[0].postulantes.forEach {
                        postulantesList.add(it)
                    }
                    postulantesArray = postulantesList.toTypedArray()
                    println("Tamaño 0 : " + postulantesArray.size)
                    itemsPostulantes = postulantesArray

                    itemsPostulantes.forEach {
                        println(it)
                    }
                }

                println(resp[0].postulantes)
            }

        }
    }
}