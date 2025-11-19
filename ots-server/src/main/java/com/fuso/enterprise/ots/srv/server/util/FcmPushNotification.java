package com.fuso.enterprise.ots.srv.server.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmPushNotification {
    public final static String AUTH_KEY_FCM = "AAAAVu0DWNU:APA91bGAqzKDyGSmu_mG8iB2BngvGMmrv_VetdG6yhxr9jvFAX-ZzNwXQW22bTR9vbJ8X00u50eeHIn9Cjm9JzuCtKyVzpw0_MPFpkrftj4SyaS_MLVCjpev6uqPzHWr9AY5xJhAFgYU";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static String sendPushNotification(String deviceToken,String title,String message)
            throws IOException, JSONException {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", deviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title", title); // Notification title
        info.put("body",message); // Notification
                                                                // body
        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            result = "Failed";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;
    }
}
