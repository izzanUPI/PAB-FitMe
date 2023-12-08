package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fitme.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
        binding = ActivityRegisterBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );

        dataHelper = new DataHelper ( this );

        binding.RegisterBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText ().toString ();
                String password = binding.password.getText ().toString ();
                String username = binding.username.getText().toString();

                if(email.equals ( "" ) || password.equals ( "" )){
                    Toast.makeText ( Register.this, "Isikan Semua", Toast.LENGTH_SHORT ).show ();
                }else {
                    Boolean checkUserEmail = dataHelper.checkEmail ( email );
                    if(checkUserEmail == false){
                        Boolean insert = dataHelper.insertData ( email, password, username );
                        if(insert == true){
                            Toast.makeText ( Register.this, "Register Success", Toast.LENGTH_SHORT ).show ();
                            Intent intent = new Intent(getApplicationContext (), Login.class);
                            startActivity ( intent );
                        }else{
                            Toast.makeText ( Register.this, "Register Failed", Toast.LENGTH_SHORT ).show ();
                        }
                    }else{
                        Toast.makeText ( Register.this, "User Sudah Terdaftar", Toast.LENGTH_SHORT ).show ();
                    }
                }
            }
        } );
        binding.KembaliBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (), Login.class);
                startActivity ( intent );
            }
        } );


    }

}