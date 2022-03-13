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
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_contratar.*

class Contratar : AppCompatActivity()  {
    var postulantes: ArrayList<String>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        //Syncr
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contratar)

        val textView: TextView = findViewById(R.id.textView2)

        //Recibe los postulantes seleccionados
        postulantes = intent.getStringArrayListExtra("Seleccionados")

        //Muestra a los postulantes seleccionados para contratar
        mostrarPostulantes(textView, postulantes)

    }

    fun mostrarPostulantes(textView: TextView, postulantes: ArrayList<String>?) {
        var cadena: String = ""
        if (postulantes != null) {
            for (postulante in postulantes) {
                cadena += (postulante + "\n")
            }
        }
        textView.text = cadena
    }

    fun onClick(p0: View) {
        //Contratar
        when(p0.id ){
            R.id.btnConfirmarContrata ->{
                contratar(postulantes)

                Toast.makeText(getApplicationContext(), "Contratos confirmados", Toast.LENGTH_SHORT).show()
                val main = Intent(this, MainActivity::class.java)
                startActivity(main)
            }
            else ->{
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG)
            }
        }

    }
    fun contratar(listaContratados: ArrayList<String>?) {
        val bundle = intent.extras
        val nombreProject = bundle?.getString("NombreProyecto").toString()

        listaContratados?.forEach {
            val url = "https://telechambac19.herokuapp.com/contratar?user=" + it + "&proyecto=" + nombreProject
            val (request, response, result) = Fuel.get(url).responseString()

            when (result) {

                is Result.Failure -> {
                    Toast.makeText(this, "Error de conexion al contratar", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}