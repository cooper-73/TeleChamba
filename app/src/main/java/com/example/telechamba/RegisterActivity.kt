package com.example.telechamba

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    }

    fun onClick(p0: View?) {
        val emailResult: EditText = findViewById(R.id.etIngresarEmail)
        val passwordResult: EditText = findViewById(R.id.etIngresarContraseña)
        val nameResult: EditText = findViewById(R.id.etIngresarNombre)
        val lastNameResult: EditText = findViewById(R.id.etIngresarApellidos)
        val email = emailResult.text
        val password = passwordResult.text
        val name = nameResult.text
        val lastName = lastNameResult.text

        val url = "https://telechambac19.herokuapp.com/register?usr="+email.toString()+"&pas="+password.toString()+"&nombre="+name.toString()+"&apellido="+lastName.toString()

        if(email.isEmpty() or password.isEmpty() or name.isEmpty() or lastName.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
        else {
            url.httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            val gson = Gson()
                            var resp = gson.fromJson(result.value, LoginClass::class.java)
                            if(resp.msg == "user created") {
                                Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            } else if(resp.msg == "user exist"){
                                Toast.makeText(this, "El usuario ya fue registrado anteriormente", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }
    }

}