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


public class RequestAppointment extends AppCompatActivity {

    EditText etn,etpn,eta,etp;
    String hun,un,sno,n,a,p,pn,g,em, name;
    Button b;
    int rsn,hrsn;
    List<String> hn=new ArrayList<>();
    List<String> ls=new ArrayList<>();
    List<String> lsh=new ArrayList<>();
    Spinner sp;
    private ProgressDialog progressDialog,pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_appointment);

        Bundle bundle=getIntent().getExtras();
        hun=bundle.getString("hun");
        un=bundle.getString("un");
        sno=bundle.getString("sno");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        etn= (EditText) findViewById(R.id.eTrn);
        eta= (EditText) findViewById(R.id.eTra);
        etp= (EditText) findViewById(R.id.eTrpr);
        etpn= (EditText) findViewById(R.id.eTpn);
        sp= (Spinner) findViewById(R.id.sprgender);
        List<String> l=new ArrayList<String>();
        l.add("Female");
        l.add("Male");
        l.add("Other");
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,l);
        sp.setAdapter(ad);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
        DatabaseReference mref=ref.child(un);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Requests").getValue().toString().equals("new")){
                    em=dataSnapshot.child("E-mail").getValue().toString().trim();
                    name=dataSnapshot.child("Name").getValue().toString().trim();
                    for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                        if (child1.getKey().toString().equals("Requests")) {
                            for (DataSnapshot child2 : child1.getChildren()) {
                                ls.add(child2.getKey().toString());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference href = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        DatabaseReference hmref=href.child(hun);
        hmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Requests").getValue().toString().equals("new")){
                    for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                        if (child1.getKey().toString().equals("Requests")) {
                            for (DataSnapshot child2 : child1.getChildren()) {
                                lsh.add(child2.getKey().toString());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference hnref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
        DatabaseReference hnmref=hnref.child(hun);
        hnmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hn.add(dataSnapshot.child("Name").getValue().toString().trim());
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        b= (Button) findViewById(R.id.brequest);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd = new ProgressDialog(RequestAppointment.this);
                pd.setMessage("Requesting Please Wait...");
                pd.show();
                n=etn.getText().toString().trim();
                a=eta.getText().toString().trim();
                p=etp.getText().toString().trim();
                pn=etpn.getText().toString().trim();
                g=sp.getSelectedItem().toString().trim();
                int flag=1;
                if (n.length() == 0) {
                    etn.setError("This field can not be left empty");
                    etn.requestFocus();
                    flag = 0;
                }
                if (a.length() == 0) {
                    eta.setError("This field can not be left empty");
                    eta.requestFocus();
                    flag = 0;
                }
                if (p.length() == 0) {
                    etp.setError("This field can not be left empty");
                    etp.requestFocus();
                    flag = 0;
                }
                if (pn.length()!=10) {
                    etpn.setError("Please enter a valid phone number");
                    etpn.requestFocus();
                    flag = 0;
                }
                if (pn.length() == 0) {
                    etpn.setError("This field can not be left empty");
                    etpn.requestFocus();
                    flag = 0;
                }

                if(flag==1) {
                    int length = ls.size();
                    if (length != 0) {
                        rsn = Integer.parseInt(ls.get(length - 1)) + 1;
                    } else {
                        rsn = 1;
                    }
                    int length1 = lsh.size();
                    if (length1 != 0) {
                        hrsn = Integer.parseInt(lsh.get(length1 - 1)) + 1;
                    } else {
                        hrsn = 1;
                    }

                    String rs = Integer.toString(rsn);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                    DatabaseReference mref = ref.child(un).child("Requests").child(rs);
                    DatabaseReference dref = mref.child("HospitalName");
                    dref.setValue(hn.get(0));
                    dref = mref.child("Status");
                    dref.setValue("Pending");
                    dref = mref.child("Queue");
                    dref.setValue(0);
                    dref = mref.child("Hospital");
                    dref.setValue(hun);


                    String hrs = Integer.toString(hrsn);
                    DatabaseReference href = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                    DatabaseReference hmref = href.child(hun).child("Requests").child(hrs);
                    DatabaseReference hdref = hmref.child("Username");
                    hdref.setValue(un);
                    hdref = hmref.child("Name");
                    hdref.setValue(n);
                    hdref = hmref.child("Age");
                    hdref.setValue(a);
                    hdref = hmref.child("Doctorno");
                    hdref.setValue(sno);
                    hdref = hmref.child("Problem");
                    hdref.setValue(p);
                    hdref = hmref.child("Phone no");
                    hdref.setValue(pn);
                    hdref = hmref.child("Gender");
                    hdref.setValue(g);
                    hdref = hmref.child("Status");
                    hdref.setValue("Pending");
                    hdref = hmref.child("Requestno");
                    hdref.setValue(rs);

                    dref = mref.child("Requestno");
                    dref.setValue(hrs);

                    pd.dismiss();
                    Toast.makeText(RequestAppointment.this, "Your request has been send", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RequestAppointment.this, Tabbed.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("X", un);
                    bundle.putString("E",em);
                    bundle.putString("N",name);
                    i.putExtras(bundle);
                    startActivity(i);
                }else{
                    pd.dismiss();
                }
            }
        });

    }
}
