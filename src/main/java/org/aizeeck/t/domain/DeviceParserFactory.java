package org.aizeeck.t.domain;

import org.aizeeck.t.config.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aizeeck on 9/13/18.
 */

public class DeviceParserFactory {

    private static final String GET_DEV = Configuration.getInstance().getProperty("GET_DEV");
    private static final String CLOUD = Configuration.getInstance().getProperty("CLOUD");

    public List<Device> parse(Map<String, String> cookies) {
        JSONObject devsJSONObject = null;
        try {
            String jsonElement = Jsoup.connect("https://www.mytesy.com/?do=get_dev")
                    .cookies(cookies)
                    .get().body().text();
            JSONObject jsonObject = new JSONObject(jsonElement);
            devsJSONObject = jsonObject.getJSONObject("device");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Device> devices = new ArrayList<>();
        if (devsJSONObject == null) {
            return devices;
        }
        JSONArray keys = devsJSONObject.names();
        for (int i = 0; i < keys.length(); i++) {
            try {
                JSONObject devJSONObject = devsJSONObject.getJSONObject(keys.getString(i));
                String jsonElementSum = Jsoup.connect(CLOUD
                        + devJSONObject.getString("id")
                        + "/CONNECT/calcRes")
                        .cookies(cookies)
                        .get().body().text();
                if (jsonElementSum.equals(new String(""))) {
                    continue;
                }
                JSONObject jsonObjectSum = new JSONObject(jsonElementSum);

                devJSONObject.put("sum", jsonObjectSum.getInt("sum"));
                Device device = new Device(devJSONObject);
                devices.add(device);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return devices;
    }
}
