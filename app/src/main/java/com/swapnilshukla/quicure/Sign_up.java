package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sign_up extends AppCompatActivity {

    Spinner sp;
    Button bsign;
    int c=0;
    EditText eml,name,mno,pass1,pass2,uname,sq,ans;
    String un,hos,n;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sp= (Spinner) findViewById(R.id.spinner);
        List<String> l=new ArrayList<String>();
        l.add("Yes");
        l.add("No");
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,l);
        sp.setAdapter(ad);

        bsign= (Button) findViewById(R.id.bsignup);
        progressDialog = new ProgressDialog(this);
        mno= (EditText) findViewById(R.id.editText7);
        pass1= (EditText) findViewById(R.id.editText4);
        pass2= (EditText) findViewById(R.id.editText6);
        uname = (EditText)findViewById(R.id.editText5);
        name= (EditText) findViewById(R.id.eTname);
        eml=(EditText)findViewById(R.id.editText3);
        sq= (EditText) findViewById(R.id.editText9);
        ans= (EditText) findViewById(R.id.editText10);

        bsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 1;

                //checking of every textfield have value
                String emailstr = eml.getText().toString();
                String emailpattern="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                if (name.getText().toString().length() == 0) {
                    name.setError("Name should not be empty");
                    name.requestFocus();
                    flag = 0;
                }
                if (uname.getText().toString().length() == 0) {
                    uname.setError("Name should not be empty");
                    uname.requestFocus();
                    flag = 0;
                }
                if (mno.getText().toString().length() != 10) {
                    mno.setError("Phone number should be of 10 digit");
                    mno.requestFocus();
                    flag = 0;
                }
                if (pass1.getText().toString().length() < 8) {
                    pass1.setError("Password should be atleast of 8 chracter");
                    pass1.requestFocus();
                    flag = 0;
                }
                if (pass2.getText().toString().length() == 0) {
                    pass2.setError("Please confirm password");
                    pass2.requestFocus();
                    flag = 0;
                }
                if (!pass1.getText().toString().equals(pass2.getText().toString())) {
                    pass2.setError("Password does not match");
                    pass2.requestFocus();
                    flag = 0;
                }
                if (emailstr.matches(emailpattern)) {

                } else {
                    eml.setError("Invalid Email");
                    eml.requestFocus();
                    flag = 0;
                }
                if (sq.getText().toString().length() == 0) {
                    sq.setError("Please confirm password");
                    sq.requestFocus();
                    flag = 0;
                }
                if (ans.getText().toString().length() == 0) {
                    ans.setError("Please confirm password");
                    ans.requestFocus();
                    flag = 0;
                }

                //final condition
                if (flag == 1)
                {
                    progressDialog.setMessage("Registering Please Wait...");
                    progressDialog.show();
                    final String emal =eml.getText().toString().trim();
                    n=name.getText().toString().trim();
                    un=uname.getText().toString().trim();
                    final String password = pass1.getText().toString().trim();
                    final String mobile=mno.getText().toString().trim();
                    final String ssq=sq.getText().toString().trim();
                    final String sans=ans.getText().toString().trim();
                    hos = sp.getSelectedItem().toString();

                    //checking for existing username
                    DatabaseReference mref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                    mref.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                String uname = child.getKey().toString().trim();
                                if (un.equals(uname)) {
                                    progressDialog.dismiss();
                                    c=1;
                                    break;
                                }
                            }
                            if(c==1){
                                Toast.makeText(Sign_up.this, "The username already exists", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_LONG).show();
                            }
                            else if(!un.equals("Quicure")){
                                //Entry in database
                                DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference ref = myref.child("Accounts");
                                DatabaseReference mrefchid = ref.child(un);
                                DatabaseReference mrefc = mrefchid.child("E-mail");
                                mrefc.setValue(emal);
                                mrefc = mrefchid.child("Password");
                                mrefc.setValue(password);
                                mrefc = mrefchid.child("Name");
                                mrefc.setValue(n);
                                mrefc = mrefchid.child("Mobile");
                                mrefc.setValue(mobile);
                                mrefc=mrefchid.child("Hos");
                                mrefc.setValue(hos);
                                mrefc=mrefchid.child("Security Question");
                                mrefc.setValue(ssq);
                                mrefc=mrefchid.child("Answer");
                                mrefc.setValue(sans);
                                if(hos.equals("No"))
                                {
                                    mrefc=mrefchid.child("Requests");
                                    mrefc.setValue("new");
                                }
                                n=un;
                                un="Quicure";
                                c=0;
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                                if (hos.equals("No")) {
                                    Intent i = new Intent(Sign_up.this, Login.class);
                                    startActivity(i);
                                } else if(!n.equals("Quicure")){
                                    DatabaseReference href = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference fref=href.child("Hospitals");
                                    DatabaseReference cref = fref.child(n);
                                    cref.setValue("new");
                                    Intent in=new Intent(Sign_up.this,HospitalRegistrations.class);
                                    in.putExtra("N",n);
                                    startActivity(in);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }

                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_LONG).show();
                }

            }

        });

    }
}