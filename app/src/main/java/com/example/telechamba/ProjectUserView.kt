package com.example.telechamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class ProjectUserView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_user_view)

        //Recibir la variable nombreEmpresa de la activity Main
        val bundle = intent.extras
        val nombreEmpresa = bundle?.getString("nombre").toString()
        val descripcion = bundle?.getString("descripcion").toString()
        val rubro = bundle?.getString("rubro").toString()
        val sueldo = bundle?.getString("sueldo").toString()
        val vacantes = bundle?.getString("vacantes").toString()

        val tNombreEmpresa: TextView = findViewById(R.id.textEmpresaMostrar)
        val tDescripcion: TextView = findViewById(R.id.textDescripcionMostrar)
        val tRubro: TextView = findViewById(R.id.textRubroMostrar)
        val tSueldo: TextView = findViewById(R.id.textSueldoMostrar)
        val tVacantes: TextView = findViewById(R.id.textVacantesMostrar)

        tNombreEmpresa.text = nombreEmpresa
        tDescripcion.text = descripcion
        tRubro.text = rubro
        tSueldo.text = sueldo
        tVacantes.text = vacantes

        val btnPostular: Button = findViewById(R.id.buttonPostular)

        btnPostular.setOnClickListener {
            val url = "https://telechambac19.herokuapp.com/postular?user="+Usuario.username+"&proyecto="+nombreEmpresa

            url.httpGet().responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        val gson = Gson()
                        var resp = gson.fromJson(result.value, PostularClass::class.java)
                        if(resp.msg == "Postulacion completada") {
                            Toast.makeText(getApplicationContext(), "Postulacion completada!", Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Postulacion no completada", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
