package com.ruskaof.client.util;

import com.ruskaof.client.ClientApi;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Localisator {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", ClientApi.getInstance().getLocale());

    public final String disconnection = convertToUTF(resourceBundle.getString("error.disconnection"));
    public final String register = convertToUTF(resourceBundle.getString("button.register"));
    public final String login = convertToUTF(resourceBundle.getString("label.login"));
    public final String password = convertToUTF(resourceBundle.getString("label.password"));
    public final String enter = convertToUTF(resourceBundle.getString("button.enter"));
    public final String takeAccount = convertToUTF(resourceBundle.getString("label.take_account"));

    public String get(String key) {

        return convertToUTF(resourceBundle.getString(key));

    }

    private String convertToUTF(String s) {
        return new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
