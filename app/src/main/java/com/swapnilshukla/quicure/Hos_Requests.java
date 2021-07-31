package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hos_Requests extends AppCompatActivity {

    List<String> prob=new ArrayList<>();
    List<String> sno=new ArrayList<>();
    List<String> uname=new ArrayList<>();
    List<String> usno=new ArrayList<>();
    List<String> dno=new ArrayList<>();
    private ProgressDialog progressDialog,pd;
    ListView lv;
    TextView tvn,tvp,tvpn,tvg,tva,tvd;
    String un,name,age,pn,g,q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos__requests);
        lv= (ListView) findViewById(R.id.lvhr);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        Bundle bundle = getIntent().getExtras();
        un = bundle.getString("X");

        DatabaseReference href = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        DatabaseReference hmref=href.child(un).child("Requests");
        hmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.getValue().toString().equals("new")){
                    for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                        if(child1.child("Status").getValue().toString().trim().equals("Pending")){
                            sno.add(child1.getKey().toString());
                            prob.add(child1.child("Problem").getValue().toString().trim());
                            uname.add(child1.child("Username").getValue().toString().trim());
                            usno.add(child1.child("Requestno").getValue().toString().trim());
                            dno.add(child1.child("Doctorno").getValue().toString().trim());
                        }
                    }
                }
                lv.setAdapter(new ArrayAdapter<String>(Hos_Requests.this, android.R.layout.simple_list_item_1, prob));
                progressDialog.dismiss();
                if(prob.isEmpty()){
                    Toast.makeText(Hos_Requests.this, "There are no requests", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                pd = new ProgressDialog(Hos_Requests.this);
                pd.setMessage("Please Wait...");
                pd.show();
                LayoutInflater layoutInflater = LayoutInflater.from(Hos_Requests.this);
                View promptView = layoutInflater.inflate(R.layout.dialog_request, null);
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Hos_Requests.this);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setTitle("Request Information:");
                tvn= (TextView) promptView.findViewById(R.id.tVn);
                tvp= (TextView) promptView.findViewById(R.id.tVp);
                tvpn= (TextView) promptView.findViewById(R.id.tVpn);
                tva= (TextView) promptView.findViewById(R.id.tVa);
                tvg= (TextView) promptView.findViewById(R.id.tVg);
                tvd= (TextView) promptView.findViewById(R.id.tVrd);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                DatabaseReference mref=ref.child(un).child("Requests").child(sno.get(position));
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name=dataSnapshot.child("Name").getValue().toString();
                        g=dataSnapshot.child("Gender").getValue().toString();
                        age=dataSnapshot.child("Age").getValue().toString();
                        pn=dataSnapshot.child("Phone no").getValue().toString();
                        tvn.setText(name);
                        tva.setText(age);
                        tvg.setText(g);
                        tvp.setText(prob.get(position));
                        tvpn.setText(pn);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                        final DatabaseReference rref=ref.child(un).child("Doctors").child(dno.get(position));
                        rref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String d = dataSnapshot.child("Name").getValue().toString().trim();
                                tvd.setText(d);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                                pd.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Accept request", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //accepting code
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                                final DatabaseReference rref=ref.child(un).child("Doctors").child(dno.get(position));
                                rref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        q=dataSnapshot.child("Queue").getValue().toString().trim();
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                                        DatabaseReference mref=ref.child(un).child("Requests").child(sno.get(position));
                                        DatabaseReference href=mref.child("Status");
                                        href.setValue("Confirmed");

                                        int qn=Integer.parseInt(q);
                                        qn=qn+1;
                                        q=Integer.toString(qn);
                                        DatabaseReference r=ref.child(un).child("Doctors").child(dno.get(position)).child("Queue");
                                        r.setValue(q);

                                        DatabaseReference uref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                                        DatabaseReference umref=uref.child(uname.get(position)).child("Requests").child(usno.get(position));
                                        DatabaseReference uhref=umref.child("Status");
                                        uhref.setValue("Confirmed");
                                        uhref=umref.child("Queue");
                                        uhref.setValue(q);
                                        Intent i = new Intent(Hos_Requests.this, Hos_Requests.class);
                                        i.putExtra("X", un);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // create an alert dialog
                android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });
    }
}
