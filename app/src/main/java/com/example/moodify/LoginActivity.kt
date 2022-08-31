package com.example.moodify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var loginButton: Button?= null
    var register: TextView?= null
    lateinit var username: EditText
    lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        loginButton= findViewById(R.id.btn_login)
        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        register=findViewById(R.id.register)

        loginButton!!.setOnClickListener {

            if(username.text.trim().isNotEmpty() || password.text.trim().isNotEmpty()){


                signInUser()
            }else{
                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()}

        }

        register!!.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    fun signInUser(){
        auth.signInWithEmailAndPassword(username.text.trim().toString(), password.text.trim().toString())
            .addOnCompleteListener (this) {
                task ->
                if(task.isSuccessful){
                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this, "Authentication error" + task.exception, Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
       if(user != null){
           val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this, " ", Toast.LENGTH_SHORT).show()
        }
   }
}