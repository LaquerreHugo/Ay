package com.laquerrehugo.app.ay.views.models;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import com.laquerrehugo.app.ay.exceptions.NoTypeException;

public class Input {
    //Properties
    private final String value;
    public String getValue() {
        return value;
    }

    private Type type;
    public Type getType() {
        if (type != null)
            return type;

        for (Type type : Type.values())
            if (this.is(type))
                return this.type = type;

        throw new NoTypeException();
    }

    //Initialize
    public Input(@NonNull String input) {
        this.value = input.trim();
    }

    //Methods
    public boolean is(Type type) {
        return this.getType() == Type.Unknown;
    }

    //Enums
    public enum Type {
        //Types
        Email(new Matcher<String>() {
            @Override
            public boolean matches(String input) {
                return !TextUtils.isEmpty(input)
                        && Patterns.EMAIL_ADDRESS.matcher(input).matches();
            }
        }),
        Phone(new Matcher<String>() {
            @Override
            public boolean matches(String input) {
                return !TextUtils.isEmpty(input)
                        && Patterns.PHONE.matcher(input).matches();
            }
        }),
        Name(new Matcher<String>() {
            @Override
            public boolean matches(String input) {
                if (TextUtils.isEmpty(input))
                    return false;

                for (char c : input.toCharArray())
                    if (!Character.isLetter(c))
                        return false;

                return true;
            }
        }),
        Unknown(new Matcher<String>() {
            @Override
            public boolean matches(String input) {
                return true;
            }
        });

        //Classes
        private interface Matcher<T> {
            boolean matches(String input);
        }

        //Properties
        private Matcher<String> matcher;

        //Initialize
        Type(Matcher<String> matcher) {
            this.matcher = matcher;
        }

        //Method
        protected boolean matches(@NonNull String s) {
            return matcher.matches(s);
        }
    }
}
