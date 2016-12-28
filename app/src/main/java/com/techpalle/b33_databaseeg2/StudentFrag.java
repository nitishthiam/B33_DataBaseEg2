package com.techpalle.b33_databaseeg2;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFrag extends Fragment {
    //DECLARE ALL VARIABLES
    Spinner sp;
    Button b;
    ListView lv;
    Cursor c1, c2;
    SimpleCursorAdapter simpleCursorAdapter1, simpleCursorAdapter2;
    MyDatabase m;
    String subject = ""; //which subject user has selected?

    public StudentFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student, container, false);
        sp = (Spinner) v.findViewById(R.id.spinner1);
        b = (Button) v.findViewById(R.id.button1);
        lv = (ListView) v.findViewById(R.id.listView1);
        //CREATE DATABASE OBJECT
        m = new MyDatabase(getActivity());
        //OPEN DATABASE
        m.open();
        //INITIALIZE CURSORS C1 - >COURSES TABLE           &   C2->STUDENTS TABLE
        c1 = m.queryCourse();
        c2 = m.queryStudent("");
        //CURSOR ADAPTER   SCA1 -> SPINNER         &    SCA2->LISTVIEW
        simpleCursorAdapter1 = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                c1,
                new String[]{"cname"},
                new int[]{android.R.id.text1}
                );
        simpleCursorAdapter2 = new SimpleCursorAdapter(
                getActivity(),
                R.layout.row,
                c2,
                new String[]{"_id", "sname", "scourse"},
                new int[]{R.id.textView1, R.id.textView2, R.id.textView3}
        );
        //ESTABLISH LINK BETWEEN ADAPTERS AND DESTINATIONS
        sp.setAdapter(simpleCursorAdapter1);
        lv.setAdapter(simpleCursorAdapter2);
        //WRITE BUTTON CLICK LISTENER
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // later .... USE SUBJECT HERE, based on subject query student table
                c2 = m.queryStudent(subject);
                simpleCursorAdapter2 = new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.row,
                        c2,
                        new String[]{"_id", "sname", "scourse"},
                        new int[]{R.id.textView1, R.id.textView2, R.id.textView3}
                );
                lv.setAdapter(simpleCursorAdapter2);
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject = c1.getString(1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return v;
    }

}
