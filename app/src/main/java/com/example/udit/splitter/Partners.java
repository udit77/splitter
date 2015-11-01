package com.example.udit.splitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Udit on 25-10-2015.
 */
public class Partners extends Fragment {

    Button button;
    ListView listView;
    ArrayList<Contacts> arrayList,addedArrayList;
    MyAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        addedArrayList = getArguments().getParcelableArrayList("ArrayList");
        arrayList= new ArrayList<>();
        //arrayList=getPartners(addedArrayList); this should come from database,return only those contacts which are not added in mainList of Home.java.
        View rootView = inflater.inflate(R.layout.partners, container, false);
        listView=(ListView)rootView.findViewById(R.id.contactListPartners);
        button = (Button)rootView.findViewById(R.id.selectContactPartners);
        adapter = new MyAdapter(getActivity(),arrayList);

        return rootView;
    }

    public class MyAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Contacts> arrayList;
        final private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

        public MyAdapter(Context context,ArrayList<Contacts> arrayList){
            this.context=context;
            this.arrayList=arrayList;
            for (int i = 0; i < this.getCount(); i++) {
                itemChecked.add(i, false);
            }
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.inflated_partners, null);
                holder.namePartners = (TextView) convertView.findViewById(R.id.nameOfPersonPartners);
                holder.numberPartners = (TextView) convertView.findViewById(R.id.contactNumberPartners);
                holder.selectNamesPartners = (CheckBox)convertView.findViewById(R.id.selectNamesPartners);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.namePartners.setText(arrayList.get(position).getName().toString());
            holder.numberPartners.setText(arrayList.get(position).getPhoneNumber());

            final CheckBox cBox = (CheckBox)convertView.findViewById(R.id.selectNamesPartners);
            cBox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v.findViewById(R.id.selectNamesPartners);
                    if (cb.isChecked()) {
                        itemChecked.set(position, true);
                    }else if (!cb.isChecked()) {
                        itemChecked.set(position, false);
                    }
                }
            });
            cBox.setChecked(itemChecked.get(position));
            return convertView;
        }

    }
    public class ViewHolder{

        TextView namePartners,numberPartners;
        CheckBox selectNamesPartners;
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.selectContactPartners:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("ArrayList",arrayList);
                intent.putExtras(bundle);
                getActivity().setResult(1, intent);
                getActivity().finish();
                break;
        }
    }
}
