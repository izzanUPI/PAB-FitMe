package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fitme.databinding.ActivityLoginBinding;
import com.example.fitme.databinding.ActivityRegisterBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityLoginBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );

        dataHelper = new DataHelper(this);


        binding.LoginBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText ().toString ();
                String password = binding.password.getText ().toString ();
                if(username.equals ( "" ) || password.equals ( "" )){
                    Toast.makeText ( Login.this, "Isikan Semua", Toast.LENGTH_SHORT ).show ();
                }else{
                    Boolean checkCredentials = dataHelper.checkUsernamePassword ( username, password );
                    if(checkCredentials == true){
                        Toast.makeText ( Login.this, "Login Sukses", Toast.LENGTH_SHORT ).show ();
                        Intent intent = new Intent(getApplicationContext (), MainActivity.class);
                        intent.putExtra("user-key", username);

                        startActivity(intent);

                    }else{
                        Toast.makeText ( Login.this, "Username/Password Salah", Toast.LENGTH_SHORT ).show ();
                    }
                }

            }
        } );
        binding.RegisterBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext (), Register.class);
                startActivity(intent);
            }
        } );
    }
}