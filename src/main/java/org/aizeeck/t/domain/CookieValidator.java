package org.aizeeck.t.domain;

import org.aizeeck.t.config.Configuration;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class CookieValidator {

    private static final String HOME = Configuration.getInstance().getProperty("HOME");

    public static boolean validateDbCookies(Map<String, String> dbCookies){

        Connection.Response response;
        Document document = null;
        try {
            response = Jsoup.connect("https://www.mytesy.com/?do=home")
                    .cookies(dbCookies)
                    .method(Connection.Method.GET)
                    .execute();
                document = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (document != null && document.body().text().contains("LogOut")) {
            return true;
        } else {
            return false;
        }

    }
}
