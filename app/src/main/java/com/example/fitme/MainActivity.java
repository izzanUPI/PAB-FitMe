package com.example.fitme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitme.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ImageView imgView;

    DataHelper dataHelper;

    TextView txt;
    TextView txtNote;

    FloatingActionButton fab;
    EditText editTextNote;
    EditText EditNote;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityMainBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );

        dataHelper = new DataHelper ( this );

        fab = findViewById(R.id.floatingActionButton);
        txt = findViewById(R.id.textViewTitle);


        Intent intent = getIntent();
        String username = intent.getStringExtra("user-key");
        String exercise_name = txt.getText().toString();

        // Set click listener for the FAB
        fab.setOnClickListener(view -> {
            String retrievedNote = dataHelper.getNoteByUsername(username);

            if(retrievedNote != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogView = getLayoutInflater().inflate(R.layout.note_view_dialog, null);
                builder.setView(dialogView);
                System.out.println(retrievedNote);

                Button buttonClose = dialogView.findViewById(R.id.buttonClose);
                Button buttonEdit = dialogView.findViewById(R.id.buttonEdit);
                Button buttonSave = dialogView.findViewById(R.id.buttonSaveEdit);
                Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);
                Button buttonYes = dialogView.findViewById(R.id.buttonYes);
                Button buttonNo = dialogView.findViewById(R.id.buttonNo);

                EditNote = dialogView.findViewById(R.id.EditNote);
                txtNote = dialogView.findViewById(R.id.textViewNote);

                buttonNo.setVisibility(View.GONE);
                buttonYes.setVisibility(View.GONE);
                buttonSave.setVisibility(View.GONE);
                EditNote.setVisibility(View.GONE);

                txtNote.setText(retrievedNote);

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                buttonClose.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                buttonEdit.setOnClickListener(v -> {
                    txtNote.setVisibility(View.GONE);
                    EditNote.setVisibility(View.VISIBLE);

                    buttonEdit.setVisibility(View.GONE);
                    buttonDelete.setVisibility(View.GONE);
                    buttonSave.setVisibility(View.VISIBLE);

                    EditNote.setText(retrievedNote);


                    buttonSave.setOnClickListener(v1 -> {
                        String note = EditNote.getText().toString();
                        boolean insert = dataHelper.updateNote(username,exercise_name,note);
                        if(insert){
                            Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Note Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    });
                });

                buttonDelete.setOnClickListener(v -> {
                    buttonClose.setVisibility(View.GONE);
                    buttonDelete.setVisibility(View.GONE);
                    buttonEdit.setVisibility(View.GONE);

                    buttonNo.setVisibility(View.VISIBLE);
                    buttonYes.setVisibility(View.VISIBLE);

                    txtNote.setText("Anda Yakin?");

                    buttonYes.setOnClickListener(v1 -> {
                        boolean insert = dataHelper.deleteNote(username,exercise_name);
                        if(insert){
                            Toast.makeText(this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Note Deletion Failed", Toast.LENGTH_SHORT).show();
                        }
                    dialog.dismiss();
                    });
                    buttonNo.setOnClickListener(v1 -> {
                        dialog.dismiss();
                    });


                });

            }else {
                // Create the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogView = getLayoutInflater().inflate(R.layout.note_input_dialog, null);
                builder.setView(dialogView);

                // Find views in the dialog layout
                editTextNote = dialogView.findViewById(R.id.editTextNote);

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Set click listener for the 'Save' button inside the dialog
                Button buttonSave = dialogView.findViewById(R.id.buttonSave);
                buttonSave.setOnClickListener(v -> {
                    // Get the text from the EditText and do something with it
                    String note = editTextNote.getText().toString();
                    boolean insert = dataHelper.insertNote(username, exercise_name, note);
                    if (insert) {
                        Toast.makeText(this, "Note Created Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Note Creation Failed", Toast.LENGTH_SHORT).show();
                    }
                    // Dismiss the dialog when the 'Save' button is clicked
                    dialog.dismiss();
                });
            }
        });
    }
}