package com.swapnilshukla.quicure;

import android.content.Intent;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Changepass extends AppCompatActivity {

    EditText etop,etnp1,etnp2;
    DatabaseReference ref;
    String sun,sop,snp1,snp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
        etop= (EditText) findViewById(R.id.eToldpass);
        etnp1= (EditText) findViewById(R.id.eTnewpass1);
        etnp2= (EditText) findViewById(R.id.eTnewpass2);
        Button b2= (Button) findViewById(R.id.btchange);
        Intent intent = getIntent();
        sun=intent.getStringExtra("X");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag=1;
                sop=etop.getText().toString().trim();
                snp1=etnp1.getText().toString().trim();
                snp2=etnp2.getText().toString().trim();
                if (sop.length() == 0) {
                    etop.setError("Please confirm password");
                    etop.requestFocus();
                    flag = 0;
                }
                if (snp1.length() == 0) {
                    etnp1.setError("Please confirm password");
                    etnp1.requestFocus();
                    flag = 0;
                }
                if (snp1.length() < 8) {
                    etnp1.setError("Password should be atleast of 8 chracter");
                    etnp1.requestFocus();
                    flag = 0;
                }
                if (snp2.length() == 0) {
                    etnp2.setError("Please confirm password");
                    etnp2.requestFocus();
                    flag = 0;
                }
                if (!snp1.equals(snp2)) {
                    etnp2.setError("Password does not match");
                    etnp2.requestFocus();
                    flag = 0;
                }
                if(flag==1) {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String password = dataSnapshot.child(sun).child("Password").getValue().toString();

                                if (sop.equals(password)) {
                                    if (snp1.equals(snp2)) {
                                        DatabaseReference mref = ref.child(sun).child("Password");
                                        mref.setValue(snp1);
                                        startActivity(new Intent(Changepass.this, Tabbed.class));
                                    } else if (!snp1.equals(snp2)) {
                                        Toast.makeText(Changepass.this, "Entered passwords do not match", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (!sop.equals(password)) {
                                    Toast.makeText(Changepass.this, "Old password entered is wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError firebaseError) {

                        }


                    });
                }
            }
        });
    }
}

