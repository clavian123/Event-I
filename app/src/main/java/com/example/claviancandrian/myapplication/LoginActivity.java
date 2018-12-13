package com.example.claviancandrian.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    Button btLogin;
    Button btRegister;
    String Email;
    String Password;
    CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = txtEmail.getText().toString();
                Password = txtPassword.getText().toString();
                if(Email.isEmpty() && Password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Your Email and Password are empty", Toast.LENGTH_LONG).show();
                }
                else if(Email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Your Email is empty", Toast.LENGTH_LONG).show();
                }
                else if(Password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Your Password is empty", Toast.LENGTH_LONG).show();
                }

                else if(Data.userList.size()==0){
                    Toast.makeText(getApplicationContext(), "This Email has not been registered", Toast.LENGTH_LONG).show();
                }
                else{
                    int i;
                    for(i = 0; i<Data.userList.size(); i++) {
                        if (Email.equals(Data.userList.get(i).getEmail())) {
                            if (Password.equals(Data.userList.get(i).getPassword())) {
                                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                startActivity(intent);
                                break;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
//                        else {
//                            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
//                            break;
//                        }
                    }
                    if(i==Data.userList.size()){
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
