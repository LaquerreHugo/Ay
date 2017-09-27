package com.example.a1543702.sim.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1543702.sim.R;
import com.example.a1543702.sim.models.Contact;
import com.example.a1543702.sim.models.InputType;
import com.example.a1543702.sim.services.Contacts;

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
    @BindView(R.id.input) EditText Input;
    @BindView(R.id.input_layout) TextInputLayout InputLayout;
    @BindView(R.id.submit) Button Submit;

    //Services
    Contacts Contacts;

    //Initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        bind();
        inject();
        //init();
    }
    private void inject() {
        Contacts = new Contacts(this); //Todo: Dependency injection
    }
    private void bind() {
        ButterKnife.bind(this);
    }

    //Events
    @OnClick(R.id.submit)
    void submit() {
        if (validate()) {
            String input = getText(Input).trim();
            Contact contact = makeContact(input);

            if (contact != null) {
                Contacts.add(contact);
                finish();
            }
        }
    }

    @OnFocusChange(R.id.input)
    void onInputFocusChange(boolean hasFocus) {
        InputLayout.setHint(hasFocus ? FocusedHint : DefaultHint);
    }

    @OnEditorAction(R.id.input)
    boolean onEditorAction(int action) {
        return action == EditorInfo.IME_ACTION_DONE
                && Submit.callOnClick();
    }

    //Helpers
    private boolean validate() {
        boolean isInputEmpty = TextUtils.isEmpty(getText(Input));
        Input.setError(isInputEmpty
                ? "You're not nothing!"
                : null);

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
