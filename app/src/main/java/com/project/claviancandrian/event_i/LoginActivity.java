package com.project.claviancandrian.event_i;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText txtEmail;
    EditText txtPassword;
    Button btLogin;
    Button btRegister;
    String Email;
    String Password;
    CheckBox cbTerms;

    /**
     * TODO
     * 3. Login with Facebook
     * 4. Login with Google Account
     * 5. Custom Login for Event Organizer*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);

//        Batas Awal Firebase

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

//        Batas Akhir Firebase

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = txtEmail.getText().toString();
                Password = txtPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Result", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Result", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                }
                                // ...
                            }
                        });

//                if(Email.isEmpty() && Password.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Your Email and Password are empty", Toast.LENGTH_LONG).show();
//                }
//                else if(Email.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Your Email is empty", Toast.LENGTH_LONG).show();
//                }
//                else if(Password.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Your Password is empty", Toast.LENGTH_LONG).show();
//                }
//
//                else if(Data.userList.size()==0){
//                    Toast.makeText(getApplicationContext(), "This Email has not been registered", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    int i;
//                    for(i = 0; i<Data.userList.size(); i++) {
//                        if (Email.equals(Data.userList.get(i).getEmail())) {
//                            if (Password.equals(Data.userList.get(i).getPassword())) {
//                                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                                startActivity(intent);
//                                break;
//                            }
//                            else {
//                                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
//                                break;
//                            }
//                        }
////                        else {
////                            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
////                            break;
////                        }
//                    }
//                    if(i==Data.userList.size()){
//                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//                    }
//                }
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
}
