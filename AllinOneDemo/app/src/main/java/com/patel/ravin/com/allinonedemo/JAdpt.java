package com.patel.ravin.com.allinonedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 22-09-2016.
 */
public class JAdpt extends BaseAdapter {
    Context context;
    ArrayList<MyBean> arrayList;

    public JAdpt(Context context, ArrayList<MyBean> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;

    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.listviews,null);

        TextView con=(TextView)view.findViewById(R.id.cont);
        TextView pop=(TextView)view.findViewById(R.id.pop);
        TextView sd=(TextView)view.findViewById(R.id.shortd);

        MyBean myBean=arrayList.get(position);
        con.setText("Country :"+myBean.getContry());
        pop.setText("Population :"+myBean.getPop());
        sd.setText("Detail"+myBean.getSd());
        return view;
    }
}
