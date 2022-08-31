package com.example.moodify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var RegisterButton : Button?= null
    var login: TextView?= null
    lateinit var reg_username: EditText
    lateinit var reg_password: EditText
    lateinit var reg_cpassword: EditText
    lateinit var reg_email: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance();
        RegisterButton= findViewById(R.id.btn_register)
        login=findViewById(R.id.login)
        reg_password=findViewById(R.id.reg_password)
        reg_cpassword=findViewById(R.id.reg_cpassword)
        reg_email=findViewById(R.id.reg_email)


        RegisterButton!!.setOnClickListener {
            if(reg_email.text.trim().isNotEmpty() || reg_password.text.trim().isNotEmpty() || reg_cpassword.text.trim().isNotEmpty()){

                registerUser()


            }else{
                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()
            }
        }

        login!!.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
    fun registerUser(){

        auth.createUserWithEmailAndPassword(reg_email.text.trim().toString(), reg_password.text.trim().toString())
            .addOnCompleteListener (this){
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Registration failed" + task.exception, Toast.LENGTH_SHORT).show()
                }

            }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user != null){
            val intent = Intent(this , NotesActivity::class.java)
            startActivity(intent)
        }else{
            Log.e("user status", "user null")
        }
    }
}
