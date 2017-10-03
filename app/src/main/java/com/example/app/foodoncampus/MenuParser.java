package com.example.app.foodoncampus;

/**
 * Created by joseppmoreira on 02/10/2017.
 */

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeSet;

public class MenuParser {
    public String retrieveMenu () {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String menuJsonStr = null;

        try {
            // Construct the URL for the services.ua.pt query
            //this get's the menus from all restaurants during next week
            URL url = new URL("http://services.web.ua.pt/sas/ementas?date=week&format=json");
            // Create the request to services.ua.pt, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                menuJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                menuJsonStr = null;
            }
            menuJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the menus data, there's no point in attempting
            // to parse it.
            menuJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return menuJsonStr;
    }

    public String[] getCanteens(String menuJsonStr) throws JSONException {
        JSONObject menuJson = new JSONObject(menuJsonStr);
        //array containing all the menus
        JSONArray menusArray = menuJson.getJSONObject("menus").getJSONArray("menu");
        TreeSet<String> resultStrs = new TreeSet<>();
        for(int i = 0; i < menusArray.length(); i++) {
            JSONObject menu = menusArray.getJSONObject(i);
            String canteen = menu.getJSONObject("@attributes").getString("canteen");
            resultStrs.add(canteen);
        }
        return resultStrs.toArray(new String[resultStrs.size()]);
    }

    //returns the list containing the data
    public String getMenusDataFromJson(String menuJsonStr, String canteen)
            throws JSONException {
        JSONObject menuJson = new JSONObject(menuJsonStr);
        //array containing all the menus
        JSONArray menusArray = menuJson.getJSONObject("menus").getJSONArray("menu");
        String resultStrs = "";
        for(int i = 0; i < menusArray.length(); i++) {
            JSONObject menu = menusArray.getJSONObject(i);
            if (menu.getJSONObject("@attributes").getString("canteen").equals(canteen)) {
                String meal = menu.getJSONObject("@attributes").getString("meal");
                String date = menu.getJSONObject("@attributes").getString("date");
                String[] parts = date.split(" ");
                date = parts[1] + "/" + parts[2] + "/" + parts[3];
                String weekday = menu.getJSONObject("@attributes").getString("weekday");
                resultStrs += "\n" + weekday + ", " + date + " (" + meal + ")\n\n";
                String isDisabled = menu.getJSONObject("@attributes").getString("disabled");
                if (isDisabled.equals("0")) {
                    JSONArray menuItems = menu.getJSONObject("items").getJSONArray("item");
                    for (int j = 0; j < menuItems.length(); j++) {
                        if (!menuItems.getString(j).contains("{"))
                            resultStrs += menuItems.getString(j) + "\n";
                    }
                } else {
                    resultStrs += isDisabled + "\n";
                }
            }
        }
        return resultStrs + "\n";
    }
}
