package com.example.udit.splitter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Udit on 25-10-2015.
 */
public class PickContacts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView=null;
    Button button;
    ArrayList<Contacts> arrayList,addedArrayList,selectedArrayList;
    MyAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addedArrayList = getArguments().getParcelableArrayList("ArrayList");
        selectedArrayList= new ArrayList<Contacts>();
        View rootView = inflater.inflate(R.layout.pickcontacts, container, false);
        listView=(ListView)rootView.findViewById(R.id.contactList);
        button = (Button)rootView.findViewById(R.id.selectContact);
        getContacts();
        adapter = new MyAdapter(getActivity(),arrayList);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("ArrayList",selectedArrayList);
                intent.putExtras(bundle);
                getActivity().setResult(1, intent);
                getActivity().finish();
            }
        });


        return rootView;
    }

    public void getContacts(){
        arrayList = new ArrayList<Contacts>();
        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "  ASC";
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, null, null, order);

        String number = "", contactName = "";
        if(cur.getCount() > 0) {
            while (cur.moveToNext()) {

                contactName = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", new String[]{contactName}, null);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Contacts mContact = new Contacts();
                mContact.setName(contactName);
                mContact.setPhoneNumber(number);

                if(!arrayList.contains(mContact)&&!addedArrayList.contains(mContact))arrayList.add(mContact);

                cursor.close();
            }
        }
        cur.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "  ASC";

        CursorLoader cursorLoader = new CursorLoader(getActivity(), uri, null, null, null, order);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cur) {
        getContacts();
        adapter = new MyAdapter(getActivity(),arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class MyAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Contacts> arrayList;
        final private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

        public MyAdapter(Context context,ArrayList<Contacts> arrayList){
            this.context=context;
            this.arrayList=arrayList;
            for (int i = 0; i < this.getCount(); i++) {
                itemChecked.add(i, false); // initializes all items value with false
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
                convertView = mInflater.inflate(R.layout.inflated_pickcontact, null);
                holder.name = (TextView) convertView.findViewById(R.id.nameOfPerson);
                holder.number = (TextView) convertView.findViewById(R.id.contactNumber);
                holder.selectNames=(CheckBox)convertView.findViewById(R.id.selectNames);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(arrayList.get(position).getName().toString());
            holder.number.setText(arrayList.get(position).getPhoneNumber());

            final CheckBox cBox = (CheckBox)convertView.findViewById(R.id.selectNames);
            cBox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v.findViewById(R.id.selectNames);
                    LinearLayout inflated = (LinearLayout)v.getParent().getParent();

                    TextView selectedName=(TextView)inflated.findViewById(R.id.nameOfPerson);
                    TextView selectedNumber = (TextView)inflated.findViewById(R.id.contactNumber);
                    String clickedName = selectedName.getText().toString();
                    String clickedNumber = selectedNumber.getText().toString();
                    Contacts contacts =new Contacts();
                    contacts.setName(clickedName);contacts.setPhoneNumber(clickedNumber);
                    if (cb.isChecked()) {
                        selectedArrayList.add(contacts);
                        itemChecked.set(position, true);
                    }else if (!cb.isChecked()) {
                        selectedArrayList.remove(contacts);
                        itemChecked.set(position, false);
                    }
                }
            });
            cBox.setChecked(itemChecked.get(position));
            return convertView;
        }

    }
    public class ViewHolder{

        TextView name,number;
        CheckBox selectNames;
    }

}
