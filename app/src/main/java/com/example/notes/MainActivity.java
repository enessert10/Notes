package com.example.notes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    List<NoteClass> noteList;
    ListView listView;

    TextView tvNoteCount;


     AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        noteList = new ArrayList<>();

        tvNoteCount = findViewById(R.id.tvNoteCount);


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Notes");







        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               NoteClass noteClass =noteList.get(position);
               showUpdateDeleteDialog(noteClass.getNoteId(),noteClass.getNote());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        getDataForFirebase();
    }

    public void addNote (View view)
    {
        Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
        startActivity(intent);
    }


    public void getDataForFirebase ()
    {
        noteList.clear();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren())
                {

                     NoteClass notes =ds.getValue(NoteClass.class);

                    noteList.add(notes);
                }

                Note noteAdapter =new Note(MainActivity.this,noteList);
                tvNoteCount.setText(noteAdapter.getCount() + " Note");
                listView.setAdapter(noteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private boolean updateNote(String id,String note,String createdDate)
    {
        noteList.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes").child(id);

        NoteClass noteClass =new NoteClass(id,note,createdDate);
        reference.setValue(noteClass);

        Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();

        return true;
    }

    private boolean deleteNote (String id)
    {
        noteList.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes").child(id);
        reference.removeValue();

        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String noteId, final String note)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_note_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etUpdateNote = dialogView.findViewById(R.id.etUpdateNote);
        final TextView tvUpdateDate = dialogView.findViewById(R.id.updatedDate);
        Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);

        dialogBuilder.setTitle("'"+note+"'");
        dialog = dialogBuilder.create();
        dialog.show();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date updateDateNote = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                tvUpdateDate.setText(simpleDateFormat.format(updateDateNote));


                String etUpdateNotes = etUpdateNote.getText().toString();
                String tvUpdateDates = "Date of update: "+tvUpdateDate.getText().toString();

                if(!TextUtils.isEmpty(etUpdateNotes))
                {
                    updateNote(noteId,etUpdateNotes,tvUpdateDates);
                    dialog.dismiss();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteNote(noteId);
                dialog.dismiss();

            }
        });

    }


}
