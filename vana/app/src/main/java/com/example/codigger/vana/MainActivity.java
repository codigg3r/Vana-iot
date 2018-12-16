package com.example.codigger.vana;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity {
    public  FirebaseAuth mAuth;
    private EditText emailE;
    private EditText passwE;
    ConstraintLayout constraintLayout;

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
            constraintLayout = (ConstraintLayout) findViewById(R.id.mainLayout) ;
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
            Snackbar.make(constraintLayout, "Email veya Şifre Eksik Girildi.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
                            startActivity(dashIntent);
                        } else {


                            // If sign in fails, display a message to the user.
                            Snackbar.make(constraintLayout, "Email veya Şifre Hatalı.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        // ...
                    }
                });

    }

    public void register(View view) {
        String email = emailE.getText().toString();
        String password = passwE.getText().toString();
        if (email.isEmpty() | password.isEmpty()){
            Snackbar.make(constraintLayout, "Email veya Şifre Eksik Girildi.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
                                startActivity(dashIntent);

                            } else {

                                Snackbar.make(constraintLayout, "Kayıt Yapılamdı!.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }


                        }
                    });
        }

    }

    public void resetPass(View view) {

        //reset pass
        String email = emailE.getText().toString();
        if (email.isEmpty() ){
            Snackbar.make(constraintLayout, "Lütfen Email Adresinizi Giriniz.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email);
        Toast.makeText(this, "Email gönderildi.", Toast.LENGTH_SHORT).show();
    }
}
