package com.example.sruthi.imagegalleryapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ListView lv;
    DatabaseHandler db;
    private dataAdapter data;
    private Contact dataModel;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        db=new DatabaseHandler(Main2Activity.this);
        lv = (ListView) findViewById(R.id.list1);
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data=new dataAdapter(this, contacts);

        lv.setAdapter(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // dataModel = contacts.get(position);

               // Toast.makeText(getApplicationContext(),String.valueOf(dataModel.getID()), Toast.LENGTH_SHORT).show();

                pos=position;

                AlertDialog.Builder builder =new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();


                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.deletenamedata(contacts.get(pos).getFName());
                        contacts.clear();
                        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
                        data=new dataAdapter(Main2Activity.this, contacts);
                        lv.setAdapter(data);
                        data.notifyDataSetChanged();
                        //Toast.makeText(Main2Activity.this, ""+contacts.get(pos).getFName()+" Deleted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
