package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forgotpass1 extends AppCompatActivity {

    String un;
    int c=0;
    Button bun;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass1);
        bun= (Button) findViewById(R.id.bfp1);
        bun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un=((EditText)findViewById(R.id.eTun1)).getText().toString().trim();
                progressDialog = new ProgressDialog(Forgotpass1.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                if(un!=null) {
                    DatabaseReference mref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                    mref.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                String uname = child.getKey().toString().trim();
                                if (un.equals(uname)) {
                                    String sq=child.child("Security Question").getValue().toString().trim();
                                    String ans=child.child("Answer").getValue().toString().trim();
                                    Intent i = new Intent(Forgotpass1.this, Forgotpass.class);
                                    i.putExtra("un", un);
                                    i.putExtra("S",sq);
                                    i.putExtra("A",ans);
                                    progressDialog.dismiss();
                                    startActivity(i);
                                    c=1;
                                    break;
                                }
                            }
                            if(c!=1){
                                Toast.makeText(Forgotpass1.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }

                    });


                }
                else{
                    Toast.makeText(Forgotpass1.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
