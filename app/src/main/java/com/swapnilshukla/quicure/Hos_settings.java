package com.swapnilshukla.quicure;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hos_settings extends AppCompatActivity {

    ListView lv;
    String un, sn;
    Spinner spds;
    List<String> serial=new ArrayList<>();
    String dn,df,dt,dt1,dt2,ds;
    int sno,length;
    DatabaseReference ref,mref;
    EditText etdf, etdn, etdt1,etdt2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_settings);
        Bundle bundle = getIntent().getExtras();
        un = bundle.getString("X");
        lv = (ListView) findViewById(R.id.lvhset);
        List<String> l = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        mref=ref.child(un).child("Doctors");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1 : dataSnapshot.getChildren()) {
                    if(!child1.getValue().equals("new")) {
                        serial.add(child1.getKey().toString().trim());
                    }
                }
                length=serial.size();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        l.add("Add Doctor");
        l.add("Remove Doctor");
        l.add("Change Queue status");
        l.add("Change Password");
        l.add("Change Account information");
        l.add("Reset the Queues");
        l.add("Appointment Requests");
        l.add("Logout");
        lv.setAdapter(new ArrayAdapter<String>(Hos_settings.this, android.R.layout.simple_list_item_1, l));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    showInputDialog();
                } else if (position == 1) {
                    Intent i = new Intent(Hos_settings.this, RemoveDoctor.class);
                    i.putExtra("X", un);
                    startActivity(i);
                }else if(position == 2){
                    //hospital list
                    Intent i = new Intent(Hos_settings.this, Hospital.class);
                    i.putExtra("X", un);
                    startActivity(i);
                } else if (position == 3) {
                    Intent i = new Intent(Hos_settings.this, Changepass.class);
                    i.putExtra("X", un);
                    startActivity(i);
                } else if (position == 4) {
                    Intent i = new Intent(Hos_settings.this, HosChangeInformation.class);
                    i.putExtra("X", un);
                    startActivity(i);
                } else if (position == 5) {
                    //reset queue
                    new android.app.AlertDialog.Builder(view.getContext()).setMessage("Are you sure you want to reset all the Queues to 0?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(int i=0;i<length;i++){
                                        DatabaseReference dmref=mref.child(serial.get(i)).child("Queue");
                                        dmref.setValue(0);
                                        Toast.makeText(Hos_settings.this, "All the Queues are set to 0", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("No",null).show();

                }else if(position==6){
                    //Appointment requests
                    Intent i = new Intent(Hos_settings.this, Hos_Requests.class);
                    i.putExtra("X", un);
                    startActivity(i);
                }
                else if (position == 7) {
                    //logout
                    new android.app.AlertDialog.Builder(view.getContext()).setMessage("Are you sure you want to Logout?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sharedPreferences=getApplicationContext().getSharedPreferences("Preferences",0);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.remove("Login");
                                    editor.commit();
                                    Intent i=new Intent(Hos_settings.this,Login.class);
                                    startActivity(i);
                                }
                            }).setNegativeButton("No",null).show();
                }

            }
        });
    }

    public void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_adddoc, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Add Doctor");
        spds = (Spinner) promptView.findViewById(R.id.spd);
        List<String> l = new ArrayList<String>();
        l.add("Allergist");
        l.add("Anesthesiologist");
        l.add("Cardiologist");
        l.add("Dermatologist");
        l.add("Endocrinologist");
        l.add("Forensic pathologist");
        l.add("Gastroenterologist");
        l.add("Gynecologist");
        l.add("Hand surgeon");
        l.add("Hepatologist");
        l.add("Internist");
        l.add("Medical examiner");
        l.add("Neonatologist");
        l.add("Nephrologist");
        l.add("Neurologist");
        l.add("Obstetrician");
        l.add("Oncologist");
        l.add("Oral surgeon");
        l.add("Orthopedic surgeon");
        l.add("Otolaryngologist");
        l.add("Pathologist");
        l.add("Pediatrician");
        l.add("Physiatrist");
        l.add("Psychiatrist");
        l.add("Thoracic surgeon");
        l.add("Urologist");
        l.add("Vascular surgeon");
        ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, l);
        spds.setAdapter(ad1);

        etdn = (EditText) promptView.findViewById(R.id.eTdname);
        etdf = (EditText) promptView.findViewById(R.id.eTdfee);
        etdt1=(EditText) promptView.findViewById(R.id.eTtimeo);
        etdt2=(EditText) promptView.findViewById(R.id.eTtimec);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ins();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void ins() {
        dn = etdn.getText().toString().trim();
        df = etdf.getText().toString().trim();
        dt1 = etdt1.getText().toString().trim();
        dt2 = etdt2.getText().toString().trim();
        ds=spds.getSelectedItem().toString();
        dt=dt1.concat("-").concat(dt2);

        int flag=1;
        if (dn.length() == 0) {
            etdn.setError("Name should not be empty");
            etdn.requestFocus();
            flag = 0;
        }
        if (df.length() == 0) {
            etdf.setError("Name should not be empty");
            etdf.requestFocus();
            flag = 0;
        }
        if (dt1.length() == 0) {
            etdt1.setError("Name should not be empty");
            etdt1.requestFocus();
            flag = 0;
        }
        if(flag==1) {
            ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
            DatabaseReference mref = ref.child(un).child("Doctors");
            sn = serial.get(length - 1);
            sno = Integer.parseInt(sn);
            sno = sno + 1;
            sn = Integer.toString(sno);
            DatabaseReference mrefchild = mref.child(sn);
            DatabaseReference mrefc = mrefchild.child("Name");
            mrefc.setValue(dn);
            mrefc = mrefchild.child("Fees");
            mrefc.setValue(df);
            mrefc = mrefchild.child("Timings");
            mrefc.setValue(dt);
            mrefc = mrefchild.child("Specialization");
            mrefc.setValue(ds);
            mrefc = mrefchild.child("Queue");
            mrefc.setValue("0");
            sno = Integer.parseInt(sn);
            sno = sno + 1;
            sn = Integer.toString(sno);
            mrefchild = mref.child(sn);
            mrefchild.setValue("new");
            Toast.makeText(Hos_settings.this, "New Doctor has been added", Toast.LENGTH_SHORT).show();
        }
    }
}
