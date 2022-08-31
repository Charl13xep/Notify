package com.example.moodify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class NotesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var noteDao: NoteDao
    private lateinit var auth: FirebaseAuth
    private lateinit var adaptor: RecyclerAdaptor
    private lateinit var logout: Button
    private lateinit var user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        recyclerView= findViewById(R.id.recyclerView)
        addButton=findViewById(R.id.addButton)
        logout = findViewById(R.id.logout)
        user = FirebaseAuth.getInstance()

        noteDao = NoteDao()
        auth = Firebase.auth

        logout.setOnClickListener {
            user.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
             val intent = Intent(this, AddnotesActivity::class.java)
            startActivity((intent))
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteCollection = noteDao.noteCollection

        val currentUserId = auth.currentUser!!.uid

        val query = noteCollection.whereEqualTo("uid", currentUserId).orderBy("text", Query.Direction.ASCENDING)

        val recyclerviewOption = FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note::class.java).build()

        adaptor = RecyclerAdaptor(recyclerviewOption)
        recyclerView.adapter = adaptor

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                adaptor.deleteNote(position)
            }

        }).attachToRecyclerView(recyclerView)
    }

    override fun onStart() {
        super.onStart()
        adaptor.startListening()
    }

    override fun onStop() {
        super.onStop()
        adaptor.stopListening()
    }
}