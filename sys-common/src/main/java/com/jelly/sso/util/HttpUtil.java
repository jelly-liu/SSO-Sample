package com.jelly.sso.util;

import com.google.gson.Gson;
import com.jelly.sso.module.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jelly on 2016-9-21.
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static final String LineSeparator = System.getProperty("line.separator", "\n");

    public static String getContent(String uri, String requestMethod, int timeout) {
        StringBuffer content = null;
        URL url = null;
        HttpURLConnection conn = null;
        InputStream in = null;

        try {
            url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(timeout / 2);
            conn.setReadTimeout(timeout / 2);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();

            content = new StringBuffer();
            in = conn.getInputStream();
            BufferedReader inRd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = inRd.readLine()) != null) {
                content.append(line).append(LineSeparator);
            }
        } catch (IOException e) {
            log.error("http call error", e);
            content = null;
        } finally {
            try {
                if(in != null) in.close();
            } catch (IOException e) {
                log.error("http call error2", e);
            }
        }

        if(content != null){
            return content.toString();
        }

        return null;
    }
}
