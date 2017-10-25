package com.laquerrehugo.app.ay.views.models;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.laquerrehugo.app.ay.exceptions.NoTypeException;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

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
        return type.matches(getValue());
    }

    //Enums
    public enum Type {
        //Types
        Email(new Matcher() {
            @Override
            public boolean matches(String input) {
                return  input != null
                        && !input.isEmpty()
                        && EmailValidator.getInstance().isValid(input);
            }
        }),
        PhoneNumber(new Matcher() {
            @Override
            public boolean matches(String input) {
                return  input != null
                        && !input.isEmpty()
                        && Patterns.PHONE.matcher(input).matches();
            }
        }),
        Name(new RegexMatcher("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}")),
        Unknown(new Matcher() {
            @Override
            public boolean matches(String input) {
                return true;
            }
        });

        //Classes
        private interface Matcher {
            boolean matches(String input);
        }
        private static class RegexMatcher implements Matcher {
            //Properties
            private String Regex;

            //Initialize
            RegexMatcher(String regex) {
                this.Regex = regex;
            }

            //Methods
            @Override
            public boolean matches(String input) {
                return input != null
                        && !input.isEmpty()
                        && Pattern.matches(Regex, input);
            }
        }

        //Properties
        private Matcher matcher;

        //Initialize
        Type(Matcher matcher) {
            this.matcher = matcher;
        }

        //Method
        protected boolean matches(@NonNull String s) {
            return matcher.matches(s);
        }
    }
}
