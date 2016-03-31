package com.bytesahft.chitchat;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    String phoneNumber;
    ListView listView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = (ListView) findViewById(R.id.contact_list);
        arrayList = new ArrayList<>();
        getNumber(this.getContentResolver());
    }

    public void getNumber(ContentResolver cr)
    {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        assert phones != null;
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println(".................." + phoneNumber);
            arrayList.add(name+"\n"+phoneNumber);
        }
        phones.close(); // close cursor
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        //display contact numbers in the list
    }
}
