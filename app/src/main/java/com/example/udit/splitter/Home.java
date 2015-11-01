package com.example.udit.splitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Home extends Activity {

    Button button;
    ArrayList<Contacts> addedNameList;
    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = (Button) findViewById(R.id.addPersonButton);
        listView = (ListView) findViewById(R.id.ListView_here);

        addedNameList = new ArrayList<Contacts>();
        adapter = new MyAdapter(this,addedNameList);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,afterHome.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("ArrayList",addedNameList);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            ArrayList<Contacts> selectedNames = new ArrayList<Contacts>();
            Bundle bundle = intent.getExtras();
            selectedNames = bundle.getParcelableArrayList("ArrayList");

            for (Contacts contactDetails : selectedNames) {
                Contacts contacts = new Contacts();
                contacts.setName(contactDetails.getName());
                contacts.setPhoneNumber(contactDetails.getPhoneNumber());
                addedNameList.add(contacts);
            }
            adapter.notifyDataSetChanged();
        }
    }


    public class MyAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Contacts> arrayList;

        public MyAdapter(Context context, ArrayList<Contacts> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
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
                convertView = mInflater.inflate(R.layout.inflated_homelist, null);
                holder.nameHome = (TextView) convertView.findViewById(R.id.nameOfPersonHome);
                holder.numberHome = (TextView) convertView.findViewById(R.id.contactNumberHome);
                holder.removeNamesHome = (Button)convertView.findViewById(R.id.removeNamesHome);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.nameHome.setText(arrayList.get(position).getName().toString());
            holder.numberHome.setText(arrayList.get(position).getPhoneNumber());
            holder.removeNamesHome.setText("Remove");
            return convertView;
        }


        public class ViewHolder {
            TextView nameHome, numberHome;
            Button removeNamesHome;
        }
    }
}