package com.example.telechamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_contratar.*

class Despedir : AppCompatActivity()  {

    var trabajadores:  ArrayList<String>?  = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Syncr
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despedir)

        val textView: TextView = findViewById(R.id.textView6)

        //Recibe los postulantes para ser despedidos
        trabajadores = intent.getStringArrayListExtra("Seleccionados")

        //Muestra a los postulantes seleccionados para contratar
        mostrarPostulantes(textView, trabajadores)

    }

    fun mostrarPostulantes(textView: TextView, trabajadores: ArrayList<String>?) {
        var cadena: String = ""
        if (trabajadores != null) {
            for (trabajador in trabajadores) {
                cadena += (trabajador + "\n")
            }
        }
        textView.text = cadena
    }

    fun onClick(p0: View) {
        //Despedir
        when(p0.id ){
            R.id.btnConfirmarDespedir ->{
                val bundle = intent.extras
                val nombreProyecto = bundle?.getString("nombreProyecto").toString()

                trabajadores?.forEach {
                    val url ="https://telechambac19.herokuapp.com/despedir?user="+it+"&proyecto="+nombreProyecto
                    url.httpGet()
                        .responseString { request, response, result ->
                            when (result) {
                                is Result.Failure -> {
                                    Toast.makeText(
                                        this,
                                        "Error al cargar trabajos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }

                Toast.makeText(getApplicationContext(), "Trabajadores despedidos", Toast.LENGTH_SHORT).show()
                val main = Intent(this, MainActivity::class.java)
                startActivity(main)
            }
            else -> {
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}