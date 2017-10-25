package com.laquerrehugo.app.ay.views;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.laquerrehugo.app.ay.R;
import com.laquerrehugo.app.ay.models.Contact;
import com.laquerrehugo.app.ay.services.Contacts;
import com.laquerrehugo.app.ay.views.models.Input;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;

public class PutActivity extends Activity {
    //Strings
    @BindString(R.string.put_input_hint) String DefaultHint;
    @BindString(R.string.put_input_hint_focus) String FocusedHint;

    //Views
    @BindView(R.id.title) TextView Title;
    @BindView(R.id.subtitle) TextView Subtitle;

    @BindView(R.id.input) EditText InputField;
    @BindView(R.id.input_layout) TextInputLayout InputLayout;
    @BindView(R.id.submit) FloatingActionButton Submit;

    //Services
    Contacts Contacts;

    //Initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        bind();
        inject();
        init();
    }
    private void bind() {
        ButterKnife.bind(this);
    }
    private void inject() {
        Contacts = new Contacts(this); //Todo: Dependency injection
    }
    private void init() {
        //Load fonts for the logo
        AssetManager assets = getApplicationContext().getAssets();
        Typeface coquette = Typeface.createFromAsset(assets, "fonts/Coquette Bold.ttf");

        //Set the fonts
        Title.setTypeface(coquette);
    }

    //Events
    @OnClick(R.id.submit)
    void submit() {
        Input input = new Input(getText(InputField));

        if (input.is(Input.Type.Unknown))
            //Todo: handle gibberish input
            Toast.makeText(this, "What is that??", Toast.LENGTH_SHORT).show();
        else {
            Contact contact = new Contact();
            fillContact(contact, input);

            Contacts.add(contact);
        }
    }

    @OnFocusChange(R.id.input)
        //Changes the hint on focus, for encouragements
    void onInputFocusChange(boolean hasFocus) {
        InputLayout.setHint(hasFocus ? FocusedHint : DefaultHint);
    }


    @OnEditorAction(R.id.input)
        //Sends a click from the keyboard
    boolean onEditorAction(int action) {
        return action == EditorInfo.IME_ACTION_DONE
                && Submit.callOnClick();
    }

    //Helpers
    private void thank() {
        Intent intent = new Intent(this, ThanksActivity.class);
        startActivity(intent);
    }

    private void fillContact(Contact contact, Input input) {
        switch (input.getType()) {
            case Email:
                contact.setEmail(input.getValue());
                break;
            case PhoneNumber:
                contact.setPhone(input.getValue());
                break;
            case Name:
                contact.setName(input.getValue());
        }
    }

    String getText(EditText view) { return view.getText().toString(); }
}
