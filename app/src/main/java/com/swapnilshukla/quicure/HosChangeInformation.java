package com.swapnilshukla.quicure;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HosChangeInformation extends AppCompatActivity {

    ListView lv;
    String un;
    EditText et;
    List<String> l=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_change_information);
        Bundle bundle = getIntent().getExtras();
        un = bundle.getString("X");
        lv= (ListView) findViewById(R.id.lvhchange);
        l.add("Name");
        l.add("Address");
        l.add("Phone Number");
        l.add("E-mail id");
        lv.setAdapter(new ArrayAdapter<String>(HosChangeInformation.this, android.R.layout.simple_list_item_1, l));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position==0) {
                    //name change
                    LayoutInflater layoutInflater = LayoutInflater.from(HosChangeInformation.this);
                    View promptView = layoutInflater.inflate(R.layout.dialog_changeinfo, null);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(HosChangeInformation.this);
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder.setTitle("Enter the new name");
                    et= (EditText) promptView.findViewById(R.id.eTchange);
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //change code
                                    String n=et.getText().toString().trim();
                                    if(n.length()!=0) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                                        DatabaseReference myref = ref.child(un).child("Name");
                                        myref.setValue(n);
                                        Toast.makeText(HosChangeInformation.this, "The name is changed", Toast.LENGTH_SHORT).show();
                                    }else {
                                        et.setError("Name should not be empty");
                                        et.requestFocus();
                                    }
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
                if(position==1){
                    //Address change
                    LayoutInflater layoutInflater = LayoutInflater.from(HosChangeInformation.this);
                    View promptView = layoutInflater.inflate(R.layout.dialog_changeinfo, null);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(HosChangeInformation.this);
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder.setTitle("Enter the new Address");
                    et= (EditText) promptView.findViewById(R.id.eTchange);
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //change code
                                    String n=et.getText().toString().trim();
                                    if(n.length()!=0) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
                                        DatabaseReference myref = ref.child(un).child("Address");
                                        myref.setValue(n);
                                        Toast.makeText(HosChangeInformation.this, "The Address is changed", Toast.LENGTH_SHORT).show();
                                    }else {
                                        et.setError("Address should not be empty");
                                        et.requestFocus();
                                    }
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
                if(position==2){
                    //Phone number
                    LayoutInflater layoutInflater = LayoutInflater.from(HosChangeInformation.this);
                    View promptView = layoutInflater.inflate(R.layout.dialog_changeinfo, null);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(HosChangeInformation.this);
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder.setTitle("Enter the new phone number");
                    et= (EditText) promptView.findViewById(R.id.eTchange);
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //change code
                                    String n=et.getText().toString().trim();
                                    if(n.length()!=0){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                                    DatabaseReference myref=ref.child(un).child("Mobile");
                                    myref.setValue(n);
                                    Toast.makeText(HosChangeInformation.this, "The Phone number is changed", Toast.LENGTH_SHORT).show();
                                    }else {
                                        et.setError("Phone number should not be empty");
                                        et.requestFocus();
                                    }
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
                if(position==3){
                    //email id
                    LayoutInflater layoutInflater = LayoutInflater.from(HosChangeInformation.this);
                    View promptView = layoutInflater.inflate(R.layout.dialog_changeinfo, null);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(HosChangeInformation.this);
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder.setTitle("Enter the new email-id");
                    et= (EditText) promptView.findViewById(R.id.eTchange);
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //change code
                                    String n=et.getText().toString().trim();
                                    if(n.length()!=0){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                                    DatabaseReference myref=ref.child(un).child("E-mail");
                                    myref.setValue(n);
                                    Toast.makeText(HosChangeInformation.this, "The E-mail id is changed", Toast.LENGTH_SHORT).show();
                                    }else {
                                        et.setError("E-mail should not be empty");
                                        et.requestFocus();
                                    }
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
            }
        });
    }
}
