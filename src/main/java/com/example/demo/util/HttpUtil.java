package com.example.demo.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static InputStream urlToInputStream(URL url, Map<String, String> args) {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            if (args != null) {
                for (Map.Entry<String, String> e : args.entrySet()) {
                    con.setRequestProperty(e.getKey(), e.getValue());
                }
            }
            con.connect();
            int responseCode = con.getResponseCode();
            if (responseCode < 400 && responseCode > 299) {
                String redirectUrl = con.getHeaderField("Location");
                try {
                    URL newUrl = new URL(redirectUrl);
                    return urlToInputStream(newUrl, args);
                } catch (MalformedURLException e) {
                    URL newUrl = new URL(url.getProtocol() + "://" + url.getHost() + redirectUrl);
                    return urlToInputStream(newUrl, args);
                }
            }
            /*!!!!!*/
            inputStream = con.getInputStream();
            return inputStream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getInputStreamFromUrl(URL url){
        Map<String,String> httpHeaders= new HashMap<>();
        httpHeaders.put("Accept", "application/json");
        httpHeaders.put("User-Agent", "bootcamp");
        return urlToInputStream(url,httpHeaders);
    }

}
