package com.patel.ravin.com.allinonedemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class smsread extends AppCompatActivity {

    ListView listView;
    ArrayList<MyBean> arrayList;
    MyBean myBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsread);

        listView=(ListView)findViewById(R.id.listView2);


        arrayList=new ArrayList<>();




                Uri inboxURI = Uri.parse("content://sms/inbox");

                // List required columns
                String[] reqCols = new String[] { "_id", "address", "body" };

                // Get Content Resolver object, which will deal with Content Provider
                ContentResolver cr = getContentResolver();


                // Fetch Inbox SMS Message from Built-in Content Provider
                Cursor c = cr.query(inboxURI, reqCols, null, null, null);

                String sms=null,id=null,number=null,body=null;
                if(c.moveToFirst()) {
                    for(int i=0; i < c.getCount(); i++) {

                        body=(c.getString(c.getColumnIndexOrThrow("body")).toString());
                        number=(c.getString(c.getColumnIndexOrThrow("address")).toString());
                        id=(c.getString(c.getColumnIndexOrThrow("_id")).toString());

                        myBean=new MyBean();

                        myBean.setId(id);
                        myBean.setBody(body);
                        myBean.setNumber(number);
                        arrayList.add(myBean);
                        c.moveToNext();

                    }


                }
                c.close();
                Adpt adpt=new Adpt(smsread.this,arrayList);
                listView.setAdapter(adpt);








    }
}
