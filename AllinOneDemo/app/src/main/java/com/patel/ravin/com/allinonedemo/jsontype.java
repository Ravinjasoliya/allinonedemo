package com.patel.ravin.com.allinonedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class jsontype extends AppCompatActivity {

    ArrayList<MyBean> arrayList;
    ListView listView;
    MyBean myBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsontype);

        String jj=loadJSONFromAsset();
        Log.e("j",jj+"Test");
        listView=(ListView)findViewById(R.id.list2);
        arrayList=new ArrayList<>();
        /*
      "rank": 1,
      "country": "China",
      "population": "1,354,040,000",
      "flag": "http://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Flag_of_the_People%27s_Republic_of_China.svg/188px-Flag_of_the_People%27s_Republic_of_China.svg.png",
	   "shortdetail":"China, officially the People's Republic of China, is a sovereign state located in East Asia. It is the world's most populous country, with a population of over 1.35 billion.",
      "description": "http://en.wikipedia.org/wiki/China"
    */
        try {
            JSONObject jsonObject = new JSONObject(jj);
            JSONArray jsonArray = jsonObject.getJSONArray("CountryDetails");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                Log.e("country", data.getString("country") + "Test1");

                myBean=new MyBean();
                myBean.setContry(data.getString("country"));
                myBean.setPop(data.getString("population"));
                myBean.setSd(data.getString("shortdetail"));
                myBean.setDesc(data.getString("description"));

                arrayList.add(myBean);

              /*  HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("country", data.getString("country") + "");
                hashMap.put("population", data.getString("description"));
                hashMap.put("shortdetail", data.getString("shortdetail"));
*/
                // list.add(hashMap);
            }
        }catch (Exception e)
        {

        }
        JAdpt adpt=new JAdpt(jsontype.this,arrayList);
        listView.setAdapter(adpt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(jsontype.this,webview.class);
                intent.putExtra("des",arrayList.get(position).getDesc());
                startActivity(intent);
                Toast.makeText(jsontype.this, arrayList.get(position).getDesc(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("countrydetails.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(jsontype.this, "404", Toast.LENGTH_SHORT).show();
            return null;
        }
        return json;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jsontype, menu);
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
