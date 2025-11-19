package com.fuso.enterprise.ots.srv.server.util;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GeoLocationUtil {

    // Cache expiration time: 6 hours (in milliseconds)
    private static final long CACHE_TTL_MILLIS = 6 * 60 * 60 * 1000;

    // In-memory cache to store IP → countryCode mappings with timestamps
    private final Map<String, CachedGeoInfo> geoCache = new ConcurrentHashMap<>();

    /**
     * Extracts the real client IP address from the HTTP request.
     * Handles proxies/load balancers using X-Forwarded-For header.
     */
    public String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        // If no proxy header, use remote address
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // In case of multiple IPs (chained proxies), use the first
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0];
        }

        return ip;
    }

    /**
     * Returns the country code (e.g., "IN", "US") for a given IP address.
     * Uses a cache to avoid repeated external API calls.
     */
    public String getCountryCodeFromIp(String ip) {
        try {        	
        	// Skip geo lookup for localhost IPs
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1") || ip.equals("::1")) {
                return "IN"; // Default for local testing
            }
            
            // 1. Check if IP is already cached and not expired
            CachedGeoInfo cached = geoCache.get(ip);
            if (cached != null && (System.currentTimeMillis() - cached.timestamp) < CACHE_TTL_MILLIS) {
                return cached.countryCode; // Return cached value
            }

            // 2. IP not cached or expired — call external geolocation API
            String url = "https://ipinfo.io/" + ip + "/json";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 seconds max wait // timeout settings
            connection.setReadTimeout(3000); // 3 seconds max read

            // 3. Read response from the API
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 4. Parse JSON response and extract countryCode
            JSONObject json = new JSONObject(response.toString());
            String countryCode = json.optString("country", "IN"); // Default to "IN" on failure

            // 5. Cache the result for future use
            geoCache.put(ip, new CachedGeoInfo(countryCode));

            return countryCode;

        } catch (Exception e) {
            e.printStackTrace(); // Log error
            return "IN"; // Safe fallback if API fails
        }
    }

    /**
     * Internal helper class to store cached countryCode with a timestamp.
     */
    private static class CachedGeoInfo {
        String countryCode;
        long timestamp;

        CachedGeoInfo(String countryCode) {
            this.countryCode = countryCode;
            this.timestamp = System.currentTimeMillis(); // Store time when cached
        }
    }
}
