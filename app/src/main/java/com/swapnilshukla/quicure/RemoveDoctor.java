package com.swapnilshukla.quicure;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class RemoveDoctor extends AppCompatActivity {

    ListView lv;
    ArrayList<String> doctors=new ArrayList<>();
    List<String> sno=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_doctor);
        lv = (ListView) findViewById(R.id.lvrd);
        Bundle bundle = getIntent().getExtras();
        final String str = bundle.getString("X");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        DatabaseReference mref = ref.child(str).child("Doctors");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    if (!child1.getValue().equals("new")) {
                        sno.add(child1.getKey().toString().trim());
                        doctors.add(child1.child("Name").getValue().toString().trim());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        lv.setAdapter(new ArrayAdapter<String>(RemoveDoctor.this, android.R.layout.simple_list_item_1, doctors));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext()).setMessage("Are you sure you want to delete "+doctors.get(position)+"'s information?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                                DatabaseReference mref=ref.child(str).child("Doctors").child(sno.get(position));
                                mref.setValue("new");
                                Toast.makeText(RemoveDoctor.this, "The information has been removed", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(RemoveDoctor.this,RemoveDoctor.class);
                                i.putExtra("X",str);
                                startActivity(i);
                            }
                        }).setNegativeButton("No",null).show();
            }
        });
    }
}
