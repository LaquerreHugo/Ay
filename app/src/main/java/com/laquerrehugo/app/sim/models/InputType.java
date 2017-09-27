package com.laquerrehugo.app.sim.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;

public enum InputType {
    Email,
    Phone,
    Name;

    @Nullable
    public static List<InputType> from(@NonNull String input) {
        List<InputType> types = new ArrayList<>();
        for (InputType type : InputType.values())
            if (is(type, input))
                types.add(type);

        return types;
    }
    public static boolean is(InputType type, @NonNull String input) {
        boolean is = false;
        switch (type) {
            case Email:
                is = isEmail(input);
                break;
            case Phone:
                is = isPhone(input);
                break;
            case Name:
                is =isName(input);
                break;
        }

        return is;
    }
    private static boolean isEmail(@NonNull String input) {
        return !TextUtils.isEmpty(input)
                && Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }
    private static boolean isPhone(@NonNull String input) {
        return !TextUtils.isEmpty(input)
                && Patterns.PHONE.matcher(input).matches();
    }
    private static boolean isName(@NonNull String input) {
        if (TextUtils.isEmpty(input))
            return false;

        for (char c : input.toCharArray())
            if (!Character.isLetter(c))
                return false;

        return true;
    }
}
