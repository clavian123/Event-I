package com.project.claviancandrian.event_i;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.regisEmail)
    EditText regisEmail;
    @BindView(R.id.regisPassowrd)
    EditText regisPassowrd;
    @BindView(R.id.regisConfirmPass)
    EditText regisConfirmPass;
    @BindView(R.id.regisBtnCreate)
    Button regisBtnCreate;

//    EditText txtREmail;
//    Button btRegister;
//    EditText txtRPassword;
//    EditText txtRConfPassword;
//    EditText txtRUsername;
//    String Email, Password, ConfPassword, Username;
//    CheckBox cbTerms;

    private FirebaseAuth auth;


    /**
     * TODO
     * Harusnya Selesai
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

//        cbTerms = findViewById(R.id.cbTerms);
//        btRegister = findViewById(R.id.btRegister);
//        txtREmail = findViewById(R.id.txtREmail);
//        txtRPassword = findViewById(R.id.txtRPassword);
//        txtRUsername = findViewById(R.id.txtRUsername);
//        txtRConfPassword = findViewById(R.id.txtRConfPassword);

        auth = FirebaseAuth.getInstance();

//        btRegister.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if(Email.isEmpty()||Username.isEmpty()||Password.isEmpty()||ConfPassword.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "You need to fill all of the data!", Toast.LENGTH_LONG).show();
//                }
//                else if(!Email.contains("@")||!Email.contains(".")||Email.contains(".@")||Email.contains("@.")||!Email.contains(".com")&&!Email.contains(".co.id")&&!Email.contains(".id")){
//                    Toast.makeText(getApplicationContext(),"Invalid Email Address!", Toast.LENGTH_LONG).show();
//                }
//                else if(Password.length()<6){
//                    if(!Password.isEmpty()){
//                        Toast.makeText(getApplicationContext(),"Password need to be 6 characters at least", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                else if (!Password.equals(ConfPassword)) {
//                    Toast.makeText(getApplicationContext(), "Your Password is not the same!", Toast.LENGTH_LONG).show();
//                }
//                else if(!cbTerms.isChecked()){
//                    Toast.makeText(getApplicationContext(), "You Need to Agree to the Terms and Agreement", Toast.LENGTH_LONG).show();
//                }
//                else if(Data.userList.size()>0){
//                    for(int i=0;i<Data.userList.size();i++){
//                        if(Email.equals(Data.userList.get(i).getEmail())){
//                            Toast.makeText(getApplicationContext(), "Email has been used", Toast.LENGTH_LONG).show();
//                            break;
//                        }
//                        else{
//                            Data.userList.add(new User(Email, Username, Password));
//                            Toast.makeText(getApplicationContext(),"user: "+Data.userList.size(), Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                            break;
//                        }
//                    }
//                }
//                else if(Password.length()>=6){
//                    char a[] = (char[]) Password.toCharArray();
//                    int num=0;
//                    int i;
//                    for(i=0;i<a.length;i++){
//                        if(Character.isDigit(a[i])){
//                            num++;
//                        }
//                    }
//                    if(num==a.length||num==0) {
//                        Toast.makeText(RegisterActivity.this, "Password must contain at least 1 character and 1 number", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        Data.userList.add(new User(Email,Username,Password));
//                        Toast.makeText(getApplicationContext(),"user: "+Data.userList.size(), Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//                else{
//                    Data.userList.add(new User(Email,Username,Password));
//                    Toast.makeText(getApplicationContext(),"user: "+Data.userList.size(), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
//        });
//    }


    @OnClick(R.id.regisBtnCreate)
    public void onViewClicked() {

       String email = regisEmail.getText().toString();
       String pass = regisPassowrd.getText().toString();
       String cpass = regisConfirmPass.getText().toString();

       Boolean valid = false;

       if (pass.equals(cpass))
       {
           valid = true;
       }
       else
       {
           valid = false;
       }

        if (valid == true)
        {
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
             });
        }

    }
}
