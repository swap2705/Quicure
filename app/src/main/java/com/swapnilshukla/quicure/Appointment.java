package com.swapnilshukla.quicure;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Appointment extends Fragment {

    String un;
    List<String> n=new ArrayList<>();
    List<String> s=new ArrayList<>();
    List<String> q=new ArrayList<>();
    ListView lv;
    TextView tvn,tvq,tvs;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_appointment, container, false);
        lv= (ListView) view.findViewById(R.id.lvapp);
        un=getArguments().getString("X");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        DatabaseReference href = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
        DatabaseReference hmref=href.child(un).child("Requests");
        hmref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    n.add(child1.child("HospitalName").getValue().toString());
                    s.add(child1.child("Status").getValue().toString());
                    q.add(child1.child("Queue").getValue().toString());

                }
                if(n.isEmpty()){
                    Toast.makeText(getContext(), "Your don't have any appointment.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                lv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, n));
            }@Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View promptView = layoutInflater.inflate(R.layout.dialog_appoint, null);
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setTitle("Request Information:");
                tvn= (TextView) promptView.findViewById(R.id.tVan);
                tvq= (TextView) promptView.findViewById(R.id.tVaq);
                tvs= (TextView) promptView.findViewById(R.id.tVas);
                tvn.setText(n.get(position));
                tvs.setText(s.get(position));
                if(s.get(position).equals("Confirmed")){
                    tvq.setText("You are "+q.get(position)+" in queue");
                }
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //accepting code
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Set Alarm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
                                startActivity(i);
                            }
                        });

                // create an alert dialog
                android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });
        return view;
    }
}
