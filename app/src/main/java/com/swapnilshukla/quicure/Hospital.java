package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hospital extends AppCompatActivity {

    ListView lv;
    Button b;
    private ProgressDialog progressDialog;
    ArrayList<String> doctors=new ArrayList<>();
    List<String> sno=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        lv= (ListView) findViewById(R.id.LvHos);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        Bundle bundle = getIntent().getExtras();
        final String str = bundle.getString("X");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        DatabaseReference mref=ref.child(str).child("Doctors");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    if(!child1.getValue().equals("new")) {
                        sno.add(child1.getKey().toString().trim());
                        doctors.add(child1.child("Name").getValue().toString().trim());
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        lv.setAdapter(new ArrayAdapter<String>(Hospital.this,android.R.layout.simple_list_item_1,doctors));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent i=new Intent(Hospital.this,HospitalAppointment.class);
                i.putExtra("K",str);
                i.putExtra("No",sno.get(position));
                startActivity(i);
            }
        });
    }
}