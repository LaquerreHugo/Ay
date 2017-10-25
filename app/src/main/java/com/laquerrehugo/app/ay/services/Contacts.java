package com.laquerrehugo.app.ay.services;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.laquerrehugo.app.ay.R;
import com.laquerrehugo.app.ay.exceptions.InsertContactException;
import com.laquerrehugo.app.ay.models.Contact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Contacts {
    //Properties
    private final Context Context;

    //Initialize
    public Contacts(final Context context) {
        this.Context = context;
    }

    //Methods
    public void insert(@NonNull final Contact contact) throws InsertContactException {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        ops.addAll(getContactProviderOperations(contact));
        ops.addAll(getApplicationProviderOperations());

        // Asking the Contact provider to create a new contact
        try {
            Context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception ex) {
            throw new InsertContactException(ex);
        }
    }

    //Helpers
    private ArrayList<ContentProviderOperation> getContactProviderOperations(Contact contact) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        if (contact.getName() != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            contact.getName()).build());
        }

        //PhoneNumber
        if (contact.getPhone() != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getPhone())
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MAIN)
                    .build());
        }

        //Email
        if (contact.getEmail() != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, contact.getEmail())
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        return ops;
    }
    private ArrayList<ContentProviderOperation> getApplicationProviderOperations() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        //Note
        String note = Context.getString(R.string.contact_note_addedby) + " " + Context.getString(R.string.app_name);

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Note.DATA1, note)
                .build());

        //Meeting date
        DateFormat meetingFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date meetingDate = Calendar.getInstance().getTime();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Event.DATA, meetingFormat.format(meetingDate))
                .withValue(ContactsContract.CommonDataKinds.Event.TYPE, ContactsContract.CommonDataKinds.Event.TYPE_CUSTOM)
                .withValue(ContactsContract.CommonDataKinds.Event.LABEL,  Context.getString(R.string.contact_meeting_date))
                .build());

        return ops;
    }
}
