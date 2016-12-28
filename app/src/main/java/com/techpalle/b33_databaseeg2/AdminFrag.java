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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFrag extends Fragment {
    //DECLARE ALL VARIABLES
    EditText et1, et2, et3, et4, et5;
    Button b1, b2;
    Spinner sp;
    Cursor c;
    SimpleCursorAdapter simpleCursorAdapter;
    MyDatabase m;
    String subject; //TO REMEMBER SPINNER ITEM

    public AdminFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin, container, false);
        //INITIALIZE ALL VARIABLES
        et1 = (EditText) v.findViewById(R.id.editText1);
        et2 = (EditText) v.findViewById(R.id.editText2);
        et3 = (EditText) v.findViewById(R.id.editText3);
        et4 = (EditText) v.findViewById(R.id.editText4);
        et5 = (EditText) v.findViewById(R.id.editText5);
        b1 = (Button) v.findViewById(R.id.button1);
        b2 = (Button) v.findViewById(R.id.button2);
        sp = (Spinner) v.findViewById(R.id.spinner1);

        //CREATE DATABASE OBJECT
        m = new MyDatabase(getActivity());
        //OPEN DATABASE
        m.open();

        //GET COURSES TABLE ROWS INTO CURSOR
        c = m.queryCourse();
        simpleCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                c,
                new String[]{"cname"},
                new int[]{android.R.id.text1});
        sp.setAdapter(simpleCursorAdapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //INSERT INTO COURSES TABLE
                String cname = et1.getText().toString();
                int cdur = Integer.parseInt(et2.getText().toString());
                String ctrainer = et3.getText().toString();
                //INSERT INTO TRAINER TABLE
                m.insertCourse(cname, cdur, ctrainer);
                //CLEAN EDIT TEXTS
                et1.setText("");et2.setText("");et3.setText("");
                et1.requestFocus();
                //REFRESH ITEMS IN SPINNER
                c.requery();
            }
        });

        //SPINNER ON ITEM SELECTED LISTENER
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                subject = c.getString(1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //INSERT STUDENT DETAILS INTO STUDENTS TABLE
                String sname = et4.getText().toString();
                String semail = et5.getText().toString();
                /*Toast.makeText(getActivity(), "SUBJECT : "+subject,
                        Toast.LENGTH_LONG).show();*/
                long val = m.insertStudent(sname, semail, subject);
                Toast.makeText(getActivity(), "val : "+val, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

}
