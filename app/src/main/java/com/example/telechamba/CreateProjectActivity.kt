package com.example.telechamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class CreateProjectActivity : AppCompatActivity()   {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)

    }

    fun onClick(p0: View?) {

        when(p0?.id){
            R.id.btnCrear ->{
                val nombreProjectResult : EditText = findViewById(R.id.editText2)
                val rucResult : EditText = findViewById(R.id.editTextTextEmailAddress)
                val vacantesResult : EditText = findViewById(R.id.editTextTextEmailAddress2)
                val descripcionResult : EditText = findViewById(R.id.editText)
                val rubroResult : EditText = findViewById(R.id.editTextTextEmailAddress4)
                val sueldoResult : EditText = findViewById(R.id.editTextTextEmailAddress5)

                val nombreProject = nombreProjectResult.text
                val ruc = rucResult.text
                val vacantes = vacantesResult.text
                val rubro = rubroResult.text
                val sueldo = sueldoResult.text
                val descripcion = descripcionResult.text
                val creador = Usuario.username

                val url = "https://telechambac19.herokuapp.com/createproyect?nombre="+nombreProject.toString()+"&ruc="+ruc.toString()+"&creador="+creador.toString()+"&vacantes="+vacantes.toString()+"&descripcion="+descripcion.toString()+"&rubro="+rubro.toString()+"&sueldo="+sueldo.toString()

                if(nombreProject.isEmpty() or ruc.isEmpty() or vacantes.isEmpty() or rubro.isEmpty() or sueldo.isEmpty() or descripcion.isEmpty() or creador.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    url.httpGet()
                        .responseString { request, response, result ->
                            when (result) {
                                is Result.Failure -> {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show()
                                }
                                is Result.Success -> {
                                    val gson = Gson()
                                    var resp = gson.fromJson(result.value, LoginClass::class.java)

                                    if(resp.msg == "proyect created") {
                                        Toast.makeText(getApplicationContext(), "Proyecto creado!", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                    } else if(resp.msg == "proyect exist") {
                                        Toast.makeText(getApplicationContext(), "Proyecto ya existente", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                        }
                }
            }

            R.id.btnRegresarMenuPrincipal ->{
                val regresar = Intent(this,MainActivity::class.java)
                startActivity(regresar)

            }
        }
    }
}