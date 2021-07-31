package com.swapnilshukla.quicure;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalAppointment extends AppCompatActivity {

    TextView tv1,tv2;
    String n,qn;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_appointment);

        Bundle bundle = getIntent().getExtras();
        final String un = bundle.getString("K");
        final String sno=bundle.getString("No");
        tv1= (TextView) findViewById(R.id.tVdname);
        tv2= (TextView) findViewById(R.id.tVqueue);
        b1= (Button) findViewById(R.id.bqadd);
        b2= (Button) findViewById(R.id.badel);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        DatabaseReference mref=ref.child(un).child("Doctors");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    if(child1.getKey().equals(sno)) {
                        n=child1.child("Name").getValue().toString().trim();
                        qn=child1.child("Queue").getValue().toString().trim();

                        tv1.setText(n);
                        tv2.setText(qn);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q=Integer.parseInt(qn);
                q=q+1;
                qn=Integer.toString(q);
                DatabaseReference qref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                DatabaseReference myref=qref.child(un).child("Doctors").child(sno).child("Queue");
                myref.setValue(qn);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q=Integer.parseInt(qn);
                if(q!=0) {
                    q = q - 1;
                    qn = Integer.toString(q);
                    DatabaseReference qref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                    DatabaseReference myref = qref.child(un).child("Doctors").child(sno).child("Queue");
                    myref.setValue(qn);
                }
                else{
                    Toast.makeText(HospitalAppointment.this, "The Queue is already 0, can't reduce the queue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
