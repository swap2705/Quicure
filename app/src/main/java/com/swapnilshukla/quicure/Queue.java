package com.swapnilshukla.quicure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Queue extends AppCompatActivity {

    TextView tvn,tvf,tva,tvq;
    String n,un,hun,sno,add,fee,q;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        Bundle bundle=getIntent().getExtras();
        n=bundle.getString("N");
        un=bundle.getString("X");
        hun=bundle.getString("Una");
        sno=bundle.getString("S");

        tvn= (TextView) findViewById(R.id.tvdocname);
        tvf= (TextView) findViewById(R.id.tVfee);
        tva= (TextView) findViewById(R.id.tvdocadd);
        tvq= (TextView) findViewById(R.id.tVq);
        b= (Button) findViewById(R.id.btnappoint);

        tvn.setText(n);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    if(child1.getKey().toString().equals(hun)) {
                        add = child1.child("Address").getValue().toString();
                        for (DataSnapshot child2 : child1.getChildren()) {
                            if(child2.getKey().toString().equals("Doctors")) {
                                for (DataSnapshot child3 : child2.getChildren()) {
                                    if (child3.getKey().toString().equals(sno)) {
                                        fee = child3.child("Fees").getValue().toString();
                                        q = child3.child("Queue").getValue().toString();
                                    }
                                }
                            }
                        }
                    }
                }
                tvf.setText(fee);
                tva.setText(add);
                int qn=Integer.parseInt(q);
                qn=qn+1;
                q=Integer.toString(qn);
                tvq.setText(q);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Queue.this,RequestAppointment.class);
                i.putExtra("un",un);
                i.putExtra("hun",hun);
                i.putExtra("sno",sno);
                startActivity(i);
            }
        });
    }
}
