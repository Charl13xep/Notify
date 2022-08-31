package com.example.moodify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddnotesActivity : AppCompatActivity() {

    private lateinit var notesEdit: EditText
    private lateinit var addNoteButton: Button
    private lateinit var noteDao: NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnotes)

        notesEdit = findViewById(R.id.notesEdit)
        addNoteButton = findViewById(R.id.addNoteButton)
        noteDao= NoteDao()

        addNoteButton.setOnClickListener {
            val note = notesEdit.text.toString()
            if (note.isNotEmpty()){

                noteDao.addNote(note)
                val intent = Intent(this,NotesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}