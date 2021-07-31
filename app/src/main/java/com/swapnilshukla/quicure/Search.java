package com.swapnilshukla.quicure;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.activity_search,container,false);
        Spinner sp=(Spinner) view.findViewById(R.id.spslist);
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
        ArrayAdapter<String> ad1=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,l);
        sp.setAdapter(ad1);
        return view;
    }
}
