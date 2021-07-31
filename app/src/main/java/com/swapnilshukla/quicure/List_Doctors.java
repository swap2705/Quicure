package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class List_Doctors extends AppCompatActivity {

    String un,sl,spl;
    List<String> names=new ArrayList<>();
    List<String> sno=new ArrayList<>();
    List<String> unames=new ArrayList<>();
    ListView lv;
    private ProgressDialog progressDialog;
    int c=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__doctors);
        Bundle bundle = getIntent().getExtras();
        un = bundle.getString("X");
        sl=bundle.getString("Loc");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching Please Wait...");
        progressDialog.show();
        spl=bundle.getString("Spec");
        lv= (ListView) findViewById(R.id.lvdl);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    if((child1.child("Address").getValue().toString().toLowerCase()).contains(sl.toLowerCase())) {
                        for (DataSnapshot child2 : child1.getChildren()) {
                            if(child2.getKey().toString().equals("Doctors")) {
                                for (DataSnapshot child3 : child2.getChildren()) {
                                    if (!child3.getValue().toString().equals("new")) {
                                        if ((child3.child("Specialization").getValue().toString()).equals(spl)) {
                                            names.add(child3.child("Name").getValue().toString());
                                            sno.add(child3.getKey().toString());
                                            unames.add(child1.getKey().toString());
                                            // Toast.makeText(List_Doctors.this, child3.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (names.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(List_Doctors.this, "There is no such doctors available in this location", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    lv.setAdapter(new ArrayAdapter<String>(List_Doctors.this, android.R.layout.simple_list_item_1, names));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i=new Intent(List_Doctors.this,Queue.class);
                            i.putExtra("N",names.get(position));
                            i.putExtra("S",sno.get(position));
                            i.putExtra("Una",unames.get(position));
                            i.putExtra("X",un);
                            startActivity(i);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });


    }
}

