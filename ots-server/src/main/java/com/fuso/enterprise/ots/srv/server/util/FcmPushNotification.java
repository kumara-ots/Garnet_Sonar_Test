package com.fuso.enterprise.ots.srv.server.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FcmPushNotification {

	@Value("${fcm.push.notification.auth.key}")
	private String pushNotificationAuthKey;

	@Value("${fcm.push.notification.api.url}")
	private String pushNotificationApiUrl;

	public String sendPushNotification(String deviceToken, String title, String message)
			throws IOException, JSONException {

		URL url = new URL(pushNotificationApiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + pushNotificationAuthKey);
		conn.setRequestProperty("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("to", deviceToken.trim());
		JSONObject info = new JSONObject();

		info.put("title", title);
		info.put("body", message);
		json.put("notification", info);

		try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
			wr.write(json.toString());
			wr.flush();
		}

		int responseCode = conn.getResponseCode();
		return responseCode == 200 ? "Success" : "Failed";
	}
}
