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

class LoginActivity : AppCompatActivity(){
    val correo = "kakaroto@gmail.com"
    val cont = "1234"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun onClick(p0: View?) {

        when(p0?.id){
            R.id.btnIngresar -> {
                val emailResult: EditText = findViewById(R.id.editTextTextPersonName)
                val passwordResult: EditText = findViewById(R.id.editTextTextPassword)
                val email = emailResult.text
                val password = passwordResult.text
                val url = "https://telechambac19.herokuapp.com/login?usr="+email.toString()+"&pas="+password.toString()

                if(email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email no ingresado", Toast.LENGTH_SHORT).show()
                }
                else if(password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Contraseña no ingresada", Toast.LENGTH_SHORT).show()
                }
                else {
                    //Lanzar actividad main
                    //val apiResponse = URL(url).readText()
                    //println(apiResponse.toString())
                    url.httpGet()
                        .responseString { request, response, result ->
                            when (result) {
                                is Result.Failure -> {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show()
                                }
                                is Result.Success -> {
                                    val gson = Gson()
                                    var resp = gson.fromJson(result.value, LoginClass::class.java)
                                    if(resp.msg == "UserCorrecto") {
                                        Usuario.username = email.toString()
                                        Toast.makeText(getApplicationContext(), "Login exitoso!", Toast.LENGTH_LONG).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                }
            }
            R.id.btnRegistrar ->{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }


}