package com.swapnilshukla.quicure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forgotpass extends AppCompatActivity {

    String fp1,fp2,ans;
    Button bfp;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        Bundle bundle = getIntent().getExtras();
        final String un = bundle.getString("un");
        final String sq=bundle.getString("S");
        final String a=bundle.getString("A");
        bfp= (Button) findViewById(R.id.btfp);
        ((TextView)findViewById(R.id.tVfsq)).setText(sq);

        bfp= (Button) findViewById(R.id.btfp);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");

        bfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ans=((EditText)findViewById(R.id.eTfans)).getText().toString().trim();
                fp1=((EditText)findViewById(R.id.eTfpass1)).getText().toString().trim();
                fp2=((EditText)findViewById(R.id.eTfpass2)).getText().toString().trim();
                if(a.equals(ans)) {
                    if (fp1.length() < 8) {
                        Toast.makeText(Forgotpass.this, "The password should be more than 8 letters", Toast.LENGTH_SHORT).show();
                    } else if (fp1.equals(fp2)) {
                        DatabaseReference mref = ref.child(un).child("Password");//will recieve un by intent un means username
                        mref.setValue(fp1);
                        Toast.makeText(Forgotpass.this, "Password changed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Forgotpass.this, Login.class));
                    } else {
                        Toast.makeText(Forgotpass.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Forgotpass.this, "Answer to the security question is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
