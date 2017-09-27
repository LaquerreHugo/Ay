package com.example.a1543702.sim.models;

import android.text.TextUtils;

public class Contact {
    //Properties
    private String Name;
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public boolean hasName() {
        return !TextUtils.isEmpty(Name);
    }

    private String Email;
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public boolean hasEmail() { return !TextUtils.isEmpty(Email); }

    private String Phone;
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public boolean hasPhone() { return !TextUtils.isEmpty(Phone); }
}
