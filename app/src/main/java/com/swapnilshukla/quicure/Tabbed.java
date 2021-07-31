package com.swapnilshukla.quicure;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tabbed extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    EditText et;
    String un;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        Bundle bundle=getIntent().getExtras();
        un=bundle.getString("X");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    Search s=new Search();
                    return s;
                case 1:
                    Appointment a=new Appointment();
                    Bundle bundle1=getIntent().getExtras();
                    //profile_extract(bundle);
                    a.setArguments(bundle1);
                    return a;
                case 2:
                    Profile pp1=new Profile();
                    Bundle bundle=getIntent().getExtras();
                    //profile_extract(bundle);
                    pp1.setArguments(bundle);
                    return pp1;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Search";
                case 1:
                    return "My Appointments";
                case 2:
                    return "My Profile";
            }
            return null;
        }
    }

    public void result_code(View v) {
        Intent i3 = new Intent(Tabbed.this, Changepass.class);
        Bundle bundle=getIntent().getExtras();
        i3.putExtras(bundle);
        startActivity(i3);
    }

    public void change_name(View v){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_changeinfo, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Enter the new Name");
        et= (EditText) promptView.findViewById(R.id.eTchange);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //change code
                        String n=et.getText().toString().trim();
                        if(n.length()!=0){
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                            DatabaseReference myref=ref.child(un).child("Name");
                            myref.setValue(n);
                            Toast.makeText(Tabbed.this, "The name is changed", Toast.LENGTH_SHORT).show();
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

    public void change_email(View v){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_changeinfo, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Enter the new E-mail id");
        Bundle bundle=getIntent().getExtras();
        un=bundle.getString("X");
        et= (EditText) promptView.findViewById(R.id.eTchange);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //change code
                        String n = et.getText().toString().trim();
                        if (n.length() != 0) {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quicure-d02fa.firebaseio.com/Accounts");
                            DatabaseReference myref = ref.child(un).child("E-mail");
                            myref.setValue(n);
                            Toast.makeText(Tabbed.this, "The E-mail id is changed", Toast.LENGTH_SHORT).show();
                        } else {
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
    public void logout(View v){

       new android.app.AlertDialog.Builder(v.getContext()).setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences=getApplicationContext().getSharedPreferences("Preferences",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.remove("Login");
                        editor.commit();
                        Intent i=new Intent(Tabbed.this,Login.class);
                        startActivity(i);
                    }
                }).setNegativeButton("No",null).show();
    }
    public void search(View v){
        EditText et= (EditText) findViewById(R.id.editText8);
        Spinner sp= (Spinner) findViewById(R.id.spslist);
        final String sl=et.getText().toString().trim();
        final String spl=sp.getSelectedItem().toString().trim();
        if(sl.length()==0)
        {
            et.setError("Please fill in the location");
            et.requestFocus();
        }else {
            finish();
            Intent i = new Intent(Tabbed.this, List_Doctors.class);
            i.putExtra("Loc", sl);
            i.putExtra("Spec", spl);
            i.putExtra("X", un);
            startActivity(i);
        }
    }
}


