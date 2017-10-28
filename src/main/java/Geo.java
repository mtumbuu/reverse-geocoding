import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Geo {
    public Geo(String apiKey) {
        API_KEY = apiKey;
    }

    private final String API_KEY;
    private final String ADDRESS_NOT_FOUND = "not found";

    public String getAddress(double lat, double lng) throws IOException {
        URL obj = new URL(getAddressURL(lat, lng));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Map<String,Object> result = new ObjectMapper().readValue(con.getInputStream(), HashMap.class);
            if (result != null && result.containsKey("results")) {
                List addresses = (List) result.get("results");
                if (addresses != null && !addresses.isEmpty()) {
                    return (String) ((Map) addresses.get(0)).get("formatted_address");
                }
            }
        }
        return ADDRESS_NOT_FOUND;
    }

    private String getAddressURL(double lat, double lng) {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Latitude should be in range [-90, 90]");
        } else if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("Longitude should be in range [-180, 180];");
        }
        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?latlng=");
        urlBuilder.append(lat).append(",").append(lng).append("&key=").append(API_KEY);
        return urlBuilder.toString();
    }
}
