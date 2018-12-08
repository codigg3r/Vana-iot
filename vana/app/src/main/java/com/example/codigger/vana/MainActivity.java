package com.example.codigger.vana;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    public  FirebaseAuth mAuth;
    private EditText emailE;
    private EditText passwE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean signed = true;
        mAuth = FirebaseAuth.getInstance();
        try {
            mAuth.getCurrentUser().getEmail();
        }catch (Exception e){
            signed = false;
        }


        if(!signed){
            Toast.makeText(this, "No login", Toast.LENGTH_SHORT).show();
        } if (signed){

            Intent dashIntent = new Intent(getBaseContext(),dash.class);
            startActivity(dashIntent);
            finish();

        }
        emailE = (EditText) findViewById(R.id.editText);
        passwE = (EditText) findViewById(R.id.editText2);

    }

    public void login(View view) {

        String email = emailE.getText().toString();
        String password = passwE.getText().toString();
        if (email.isEmpty() | password.isEmpty()){
            Toast.makeText(this, "Email veya Şifre eksik girildi", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Giriş Başarılı!", Toast.LENGTH_SHORT).show();
                            Intent dashIntent = new Intent(getBaseContext(),dash.class);
                            dashIntent.putExtra("uid",mAuth.getCurrentUser().getUid());
                            dashIntent.putExtra("email",mAuth.getCurrentUser().getEmail());
                            startActivity(dashIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Giriş Yapılamadı!", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }

    public void register(View view) {
        String email = emailE.getText().toString();
        String password = passwE.getText().toString();
        if (email.isEmpty() | password.isEmpty()){
            Toast.makeText(this, "Email veya Şifre eksik girildi", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!emailE.getText().toString().isEmpty()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show();
                                Intent dashIntent = new Intent(getBaseContext(),dash.class);
                                dashIntent.putExtra("uid",mAuth.getCurrentUser().getUid());
                                dashIntent.putExtra("email",mAuth.getCurrentUser().getEmail());
                                startActivity(dashIntent);

                            } else {

                                Toast.makeText(MainActivity.this, "Kayıt Yapılamadı!", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
        }

    }

    public void resetPass(View view) {

        //reset pass
        String email = emailE.getText().toString();
        if (email.isEmpty() ){
            Toast.makeText(this, "Lütfen Email Adresinizi Giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email);
        Toast.makeText(this, "Email gönderildi.", Toast.LENGTH_SHORT).show();
    }
}
