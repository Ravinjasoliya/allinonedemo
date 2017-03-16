package com.patel.ravin.com.allinonedemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class sendcntgmail extends AppCompatActivity {

    Button readFile,writeFile,createFile;
    TextView display;
    EditText filename;
    File file;
    Uri uri=null;
    String cont="Contact Details: \n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcntgmail);

        readFile=(Button)findViewById(R.id.read);
       // createFile=(Button)findViewById(R.id.create);
        writeFile=(Button)findViewById(R.id.write);
        filename=(EditText)findViewById(R.id.fname);


        cont=readContacts();

        file = Environment.getExternalStorageDirectory();
   //     Toast.makeText(getApplicationContext(), "Sdcard Detected..", Toast.LENGTH_LONG).show();
        file = new File(file.getAbsolutePath() + "/mydir1");
        file.mkdir();
     //   Toast.makeText(getApplicationContext(), "Folder Creatred", Toast.LENGTH_LONG).show();

        file = new File(file.getAbsolutePath() + "/contact.txt");
        Toast.makeText(getApplicationContext(), "File Creatred", Toast.LENGTH_LONG).show();



       /* writeFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    file.createNewFile();
                    FileOutputStream fos=new FileOutputStream(file);
                    Log.e("filedata",cont.toString());
                    fos.write(cont.toString().getBytes());
                    fos.flush();
                    fos.close();
                    Toast.makeText(getApplicationContext(), "FileCreated", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
*/
        readFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    file.createNewFile();
                    FileOutputStream fos=new FileOutputStream(file);
                    Log.e("filedata",cont.toString());
                    fos.write(cont.toString().getBytes());
                    fos.flush();
                    fos.close();
                    Toast.makeText(getApplicationContext(), "FileCreated", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                  //  file = Environment.getExternalStorageDirectory();
//get file path for read

                   // file = new File(file.getAbsolutePath() + "/mydir1/contact.txt");
                 //   uri=Uri.parse(file.getAbsoluteFile()+"/mydir1/myfile.txt");
                   uri=Uri.parse("file://mnt/sdcard/mydir1/contact.txt");
                   // uri=Uri.parse("file:/"+file.getAbsolutePath());

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "jasoliyaravin@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT,"This is sub");
                    email.putExtra(Intent.EXTRA_TEXT, "Your Contact :");

                    //need this to prompts email client only
                    email.setType("message/rfc822");
                    FileInputStream fin = new FileInputStream(file);

                    int i = 0;
                    String s = "";
                    while (i != -1) {
                        i = fin.read();
                        if (i == -1)
                            break;
                        s += (char) i;
                    }
                    email.putExtra(Intent.EXTRA_STREAM,uri);

                    Log.e("files",s+" EOF");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public String readContacts() {

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

          cont+=name+": "+phoneNumber+"\n";

            Log.e("contact", name + phoneNumber + "null");
        }
        phones.close();
        return  cont;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sendcntgmail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
