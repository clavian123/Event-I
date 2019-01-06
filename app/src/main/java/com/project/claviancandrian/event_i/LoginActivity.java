package com.project.claviancandrian.event_i;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.btnLoginGoogle)
    Button btnLoginGoogle;
    private FirebaseAuth mAuth;

    EditText txtEmail;
    EditText txtPassword;
    Button btLogin;
    Button btRegister;
    String Email;
    String Password;
    CheckBox cbTerms;

    private final static int RC_SIGN_IN = 123;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);

// ...
//        Batas Awal Firebase

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        Batas Akhir Firebase

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.setVisibility(View.VISIBLE);

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
                                    Toast.makeText(LoginActivity.this, "Welcome " + user, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, ShowListActivity.class);
                                    intent.putExtra("source","mail");
                                    intent.putExtra("user", user.getEmail());
                                    finish();
                                    startActivity(intent);
                                    progress.setVisibility(View.GONE);
//                            updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Result", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.GONE);
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
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("tag", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("tag", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, ShowListActivity.class);
                            intent.putExtra("user", user.getEmail());
                            intent.putExtra("source","google");
                            finish();
                            startActivity(intent);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent = new Intent(this,ShowListActivity.class);
            intent.putExtra("source","mail");
            intent.putExtra("user",currentUser.getEmail());
            startActivity(intent);
        }
//        updateUI(currentUser);
    }

    @OnClick(R.id.tvForgotPassword)
    public void onViewClicked() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btnLoginGoogle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLoginGoogle:
                    signIn();
                break;
        }
    }
}
