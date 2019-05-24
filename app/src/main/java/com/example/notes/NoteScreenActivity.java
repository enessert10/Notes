package com.example.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteScreenActivity extends AppCompatActivity {

    List<NoteClass> noteList;

    EditText etUpdateNoteTitle;
    EditText etUpdateNote;
    TextView tvUpdateDate;
    Button btnUpdate;
    Button btnDelete;

    String noteId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete_note_);

        Intent intent = getIntent();

        etUpdateNoteTitle = findViewById(R.id.etUpdateNoteTitle);
        etUpdateNote = findViewById(R.id.etUpdateNote);
        tvUpdateDate = findViewById(R.id.updatedDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);


        etUpdateNoteTitle.setText(intent.getStringExtra("noteTitle"));
        etUpdateNote.setText(intent.getStringExtra("note"));

        noteId = intent.getStringExtra("noteId");



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date updateDateNote = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                tvUpdateDate.setText(simpleDateFormat.format(updateDateNote));

                String etUpdateNoteTitles = etUpdateNoteTitle.getText().toString();
                String etUpdateNotes = etUpdateNote.getText().toString();
                String tvUpdateDates = "Date of update: "+tvUpdateDate.getText().toString();

                if(!TextUtils.isEmpty(etUpdateNotes) && !TextUtils.isEmpty(etUpdateNoteTitles))
                {
                    updateNote(noteId,etUpdateNoteTitles,etUpdateNotes,tvUpdateDates);

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(NoteScreenActivity.this, "Please enter informations.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteNote(noteId);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

    }




    private boolean updateNote(String id,String noteTitle,String note,String createdDate)
    {
        //noteList.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes").child(id);

        NoteClass noteClass =new NoteClass(id,noteTitle,note,createdDate);
        reference.setValue(noteClass);

        Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();

        return true;
    }

    private boolean deleteNote (String id)
    {
        //noteList.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes").child(id);
        reference.removeValue();

        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();

        return true;
    }
}
