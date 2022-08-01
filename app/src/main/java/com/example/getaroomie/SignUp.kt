package com.example.getaroomie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    //interests

    private lateinit var movies: RadioButton
    private lateinit var music: RadioButton
    private lateinit var cycling: RadioButton
    private lateinit var swimming: RadioButton
    private lateinit var dancing: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPass = findViewById(R.id.edt_pass)
        btnSignup = findViewById(R.id.btnSignup)

        //interests being initialized
        movies = findViewById(R.id.movies)
        music = findViewById(R.id.music)
        cycling = findViewById(R.id.cycling)
        swimming = findViewById(R.id.swimming)
        dancing = findViewById(R.id.dancing)





        btnSignup.setOnClickListener{
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPass.text.toString()



            signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email: String, password: String){
    //for creating new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //for opening home page and adding user to database
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid:String){
        mDbRef = FirebaseDatabase.getInstance().reference

        mDbRef.child("user").child(uid).setValue(User(name, email, uid, movies.isChecked,music.isChecked,cycling.isChecked,swimming.isChecked,dancing.isChecked))
    }

}