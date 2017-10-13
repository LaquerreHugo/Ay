package com.laquerrehugo.app.ay.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.laquerrehugo.app.ay.models.Contact;

public class Contacts {
    private final Context Context;

    //Initialize
    public Contacts(final Context context) {
        this.Context = context;
    }

    //Methods
    public void add(@NonNull final Contact contact) {
        //Todo: add contact in the background
    }
}
