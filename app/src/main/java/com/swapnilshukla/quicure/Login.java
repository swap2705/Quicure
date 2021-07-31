package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText etUName, etPassword;
    private TextView tvSignup,tvForgot;
    private ProgressDialog progressDialog;
    ArrayList<String> doctors=new ArrayList<String>();
    String em,name;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUName = (EditText) findViewById(R.id.editText);
        etPassword = (EditText) findViewById(R.id.editText2);
        btnLogin = (Button) findViewById(R.id.button);
        tvSignup = (TextView) findViewById(R.id.textView3);
        tvForgot= (TextView) findViewById(R.id.textView2);

        progressDialog = new ProgressDialog(this);
        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
    }

    private void userLogin() {
        final String uname = etUName.getText().toString().trim();
        final String pw = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(uname)) {
            Toast.makeText(this, "Please enter ethe username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pw)) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String password = dataSnapshot.child(uname).child("Password").getValue().toString().trim();
                    String hos=dataSnapshot.child(uname).child("Hos").getValue().toString().trim();
                    em=dataSnapshot.child(uname).child("E-mail").getValue().toString().trim();
                    name=dataSnapshot.child(uname).child("Name").getValue().toString().trim();
                    if (pw.equals(password)) {
                        progressDialog.dismiss();
                        Intent i;
                        sharedPreferences=getApplicationContext().getSharedPreferences("Preferences",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("Login",uname);
                        editor.putString("E",em);
                        editor.putString("N",name);
                        editor.putString("Hos",hos);
                        editor.commit();
                        if(hos.equals("No")) {
                            i = new Intent(Login.this, Tabbed.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("X", uname);
                            bundle.putString("E",em);
                            bundle.putString("N",name);
                            i.putExtras(bundle);
                        }
                        else{
                            i=new Intent(Login.this,Hos_settings.class);
                            i.putExtra("X",uname);
                        }
                        finish();
                        startActivity(i);

                    } else if (!pw.equals(password)) {
                        Toast.makeText(Login.this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }

        });

    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            userLogin();
        }
        if(view == tvSignup){
          finish();
        startActivity(new Intent(this, Sign_up.class));
        }
        if(view == tvForgot){
            finish();
            startActivity(new Intent(this,Forgotpass1.class));
        }
    }
}
