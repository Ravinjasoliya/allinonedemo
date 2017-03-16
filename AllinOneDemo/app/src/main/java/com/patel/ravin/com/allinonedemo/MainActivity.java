package com.patel.ravin.com.allinonedemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button camera,btnsms;
    ImageView image;
    ListView listView;
    ArrayList<MyBean> arrayList;
    MyBean myBean;


    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         camera=(Button)findViewById(R.id.btnCam);
         image=(ImageView)findViewById(R.id.image);
         btnsms=(Button)findViewById(R.id.btnInbox);
        // listView=(ListView)findViewById(R.id.listView);


        arrayList=new ArrayList<>();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Adpt adpt=new Adpt(MainActivity.this,arrayList);
             //   listView.setAdapter(adpt);






            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
public  void clog(View v)
{
    startActivity(new Intent(MainActivity.this, callLogs.class));
}
public  void json(View v)
{
    startActivity(new Intent(MainActivity.this,jsontype.class));
}
public  void gomaps(View v)
{
    startActivity(new Intent(MainActivity.this,mapsLocation1.class));

}public  void files(View v)
{
    startActivity(new Intent(MainActivity.this,sendcntgmail.class));

}public  void sms(View v)
{
    startActivity(new Intent(MainActivity.this,smsread.class));

}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //fffg7
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
