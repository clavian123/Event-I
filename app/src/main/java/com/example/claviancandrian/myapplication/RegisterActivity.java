package com.example.claviancandrian.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.UserData;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText txtREmail;
    Button btRegister;
    EditText txtRPassword;
    EditText txtRConfPassword;
    EditText txtRUsername;
    String Email, Password, ConfPassword, Username;
    CheckBox cbTerms;
    String emailPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cbTerms = findViewById(R.id.cbTerms);
        btRegister = findViewById(R.id.btRegister);
        txtREmail = findViewById(R.id.txtREmail);
        txtRPassword = findViewById(R.id.txtRPassword);
        txtRUsername = findViewById(R.id.txtRUsername);
        txtRConfPassword = findViewById(R.id.txtRConfPassword);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        btRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Email = txtREmail.getText().toString();
                Username = txtRUsername.getText().toString();
                Password = txtRPassword.getText().toString();
                ConfPassword = txtRConfPassword.getText().toString();
                if(Email.isEmpty()||Username.isEmpty()||Password.isEmpty()||ConfPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "You need to fill all of the data!", Toast.LENGTH_LONG).show();
                }
                else if(!Email.contains("@")||!Email.contains(".")||Email.contains(".@")||Email.contains("@.")||!Email.contains(".com")&&!Email.contains(".co.id")&&!Email.contains(".id")){
                    Toast.makeText(getApplicationContext(),"Invalid Email Address!", Toast.LENGTH_LONG).show();
                }
                else if(Password.length()<6){
                    if(!Password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Password need to be 6 characters at least", Toast.LENGTH_LONG).show();
                    }
                }

                else if (!Password.equals(ConfPassword)) {
                    Toast.makeText(getApplicationContext(), "Your Password is not the same!", Toast.LENGTH_LONG).show();
                }
                else if(!cbTerms.isChecked()){
                    Toast.makeText(getApplicationContext(), "You Need to Agree to the Terms and Agreement", Toast.LENGTH_LONG).show();
                }
                else if(Data.userList.size()>0){
                    for(int i=0;i<Data.userList.size();i++){
                        if(Email.equals(Data.userList.get(i).getEmail())){
                            Toast.makeText(getApplicationContext(), "Email has been used", Toast.LENGTH_LONG).show();
                            break;
                        }
                        else{
                            Data.userList.add(new User(Email, Username, Password));
                            Toast.makeText(getApplicationContext(),"user: "+Data.userList.size(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }
                }
                else if(Password.length()>=6){
                    char a[] = (char[]) Password.toCharArray();
                    int num=0;
                    int i;
                    for(i=0;i<a.length;i++){
                        if(Character.isDigit(a[i])){
                            num++;
                        }
                    }
                    if(num==a.length||num==0) {
                        Toast.makeText(RegisterActivity.this, "Password must contain at least 1 character and 1 number", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Data.userList.add(new User(Email,Username,Password));
                        Toast.makeText(getApplicationContext(),"user: "+Data.userList.size(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Data.userList.add(new User(Email,Username,Password));
                    Toast.makeText(getApplicationContext(),"user: "+Data.userList.size(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
