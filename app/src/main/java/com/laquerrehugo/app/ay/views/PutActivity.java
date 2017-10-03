package com.laquerrehugo.app.ay.views;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.laquerrehugo.app.ay.R;
import com.laquerrehugo.app.ay.models.Contact;
import com.laquerrehugo.app.ay.models.InputType;
import com.laquerrehugo.app.ay.services.Contacts;

import java.util.List;

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

    @BindView(R.id.input) EditText Input;
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
        if (validate() && false) {
            String input = getText(Input).trim();
            Contact contact = makeContact(input);

            if (contact != null) {
                Contacts.add(contact);
                finish();
            }
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
    private boolean validate() {
        boolean isInputEmpty = TextUtils.isEmpty(getText(Input));
        //Todo: handle empty without errors

        return !isInputEmpty;
    }

    @Nullable
    private Contact makeContact(String input) {
        List<InputType> inputTypes = InputType.from(input);
        if (inputTypes.isEmpty())
            return null;

        Contact contact = new Contact();
        if (inputTypes.contains(InputType.Email))
            contact.setEmail(input);
        else if (inputTypes.contains(InputType.Phone))
            contact.setPhone(input);
        else if (inputTypes.contains(InputType.Name))
            contact.setName(input);

        return contact;
    }

    String getText(EditText view) { return view.getText().toString(); }
}
