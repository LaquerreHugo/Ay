package com.example.a1543702.sim.services;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.example.a1543702.sim.models.Contact;

public class Contacts {
    private final Context Context;

    //Initialize
    public Contacts(final Context context) {
        this.Context = context;
    }

    //Methods
    public void add(@NonNull final Contact contact) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        if (contact.hasName())
            intent.putExtra(ContactsContract.Intents.Insert.NAME, contact.getName());
        if (contact.hasPhone())
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getPhone());
        if (contact.hasName())
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getEmail());

        Context.startActivity(intent);
    }
}
