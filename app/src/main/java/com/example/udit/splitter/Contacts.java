package com.example.udit.splitter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Udit on 25-10-2015.
 */
public class Contacts implements Parcelable {

    String _name;
    String _phone_number;
    boolean selected;

    public Contacts(){
    }

    public Contacts(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
    }


    public Contacts(String name){
        this._name = name;
    }


    public String getName(){
        return this._name;
    }


    public void setName(String name){
        this._name = name;
    }


    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public boolean isSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected;}

    public static final Parcelable.Creator<Contacts> CREATOR = new Creator<Contacts>() {

        public Contacts createFromParcel(Parcel source) {

            Contacts contacts = new Contacts();

            contacts._name = source.readString();

            contacts._phone_number = source.readString();



            return contacts;

        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    private Contacts(Parcel in) {
        _name = in.readString();
        _phone_number = in.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(_name);
        dest.writeString(_phone_number);
    }

    @Override
    public boolean equals (Object object)
    {
        boolean result = false;

        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Contacts contacts = (Contacts) object;
            if (this._name.equals(contacts.getName()) && this._phone_number.equals(contacts.getPhoneNumber())) {
                result = true;
            }
        }
        return result;
    }
}
