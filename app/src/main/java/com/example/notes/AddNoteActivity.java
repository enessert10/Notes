package com.example.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {

    EditText etAddNote;
    TextView tvAddNoteCreatedDate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etAddNote = findViewById(R.id.etAddNote);
        tvAddNoteCreatedDate = findViewById(R.id.tvAddNoteCreatedDate);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Notes");


    }

    public void add(View view)
    {


        Date createdDateNote = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        tvAddNoteCreatedDate.setText(simpleDateFormat.format(createdDateNote));

        String note = etAddNote.getText().toString().trim();
        String createdDate = tvAddNoteCreatedDate.getText().toString();

        if(!TextUtils.isEmpty(note))
        {

            String noteId = myRef.push().getKey();

            NoteClass noteClass = new NoteClass(noteId,note,createdDate);

            myRef.child(noteId).setValue(noteClass);

            Toast.makeText(this, "Note added !", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Please enter note", Toast.LENGTH_LONG).show();
        }




        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);



    }

    /*private void writeNewNote(String noteId, String note, String createdDate) {

        NoteClass notes = new NoteClass(nonote,createdDate);


        notes.setNote(note);
        notes.setNote(createdDate);

        myRef.child("Notes").child(noteId).child("createdDate").setValue(createdDate);
        myRef.child("Notes").child(noteId).child("noteContent").setValue(note);

    }*/
}
