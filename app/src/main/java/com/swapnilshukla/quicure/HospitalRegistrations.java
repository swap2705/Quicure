package com.swapnilshukla.quicure;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HospitalRegistrations extends AppCompatActivity {

    private Button badd,bf;
    EditText etdt1,etdt2, etdn, etdf;
    Spinner spds;
    String address, un, sn, lat, lon;
    int sno=1;
    DatabaseReference ref;
    ListView lv;
    List<String> hos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registrations);
        badd = (Button) findViewById(R.id.badd);
        bf= (Button) findViewById(R.id.button2);
        lv = (ListView) findViewById(R.id.listHos);
        Bundle bundle = getIntent().getExtras();
        un = bundle.getString("N");

        DatabaseReference myref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Hospitals");
        ref = myref.child(un);

        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hos.isEmpty()){
                    Toast.makeText(HospitalRegistrations.this, "Please the doctor's information first", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(HospitalRegistrations.this, Login.class));
                }
            }
        });
        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address= ((EditText) findViewById(R.id.eTaddress)).getText().toString().trim();
                if(address.length()<0) {
                    Toast.makeText(HospitalRegistrations.this, "Please enter the address first", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference aref = ref.child("Address");
                    aref.setValue(address);
                    aref = ref.child("Requests");
                    aref.setValue("new");
                    showInputDialog();
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
        spds= (Spinner) promptView.findViewById(R.id.spd);
        List<String> l=new ArrayList<String>();
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
        ArrayAdapter<String> ad1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,l);
        spds.setAdapter(ad1);

        etdn=(EditText) promptView.findViewById(R.id.eTdname);
        etdf=(EditText) promptView.findViewById(R.id.eTdfee);
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

    public void ins(){
        String dn = etdn.getText().toString().trim();
        String df = etdf.getText().toString().trim();
        String dt1 = etdt1.getText().toString().trim();
        String dt2 = etdt2.getText().toString().trim();
        String ds=spds.getSelectedItem().toString();
        String dt=dt1.concat("-").concat(dt2);

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

            sn = Integer.toString(sno);
            DatabaseReference mrefchid = ref.child("Doctors");
            DatabaseReference mrefchild = mrefchid.child(sn);
            DatabaseReference mrefc = mrefchild.child("Fees");
            mrefc.setValue(df);
            mrefc = mrefchild.child("Name");
            mrefc.setValue(dn);
            mrefc = mrefchild.child("Timings");
            mrefc.setValue(dt);
            mrefc = mrefchild.child("Specialization");
            mrefc.setValue(ds);
            mrefc = mrefchild.child("Queue");
            mrefc.setValue("0");
            sno = sno + 1;
            sn = Integer.toString(sno);
            mrefchild = mrefchid.child(sn);
            mrefchild.setValue("new");
            hos.add(dn);
            lv.setAdapter(new ArrayAdapter<String>(HospitalRegistrations.this, android.R.layout.simple_list_item_1, hos));
        }
    }
}
