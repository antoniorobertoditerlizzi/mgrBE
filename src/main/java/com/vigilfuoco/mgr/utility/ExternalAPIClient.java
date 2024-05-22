package com.vigilfuoco.mgr.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class ExternalAPIClient {


    public static String getJsonData(String url) throws IOException {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");
            int responseCode = httpClient.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "La richiesta HTTP non è stata completata correttamente: " + responseCode;
            }
    }
}
