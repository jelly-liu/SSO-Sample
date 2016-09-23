package com.jelly.sso.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by jelly on 2016-9-21.
 */
public class StringUtil {
    private static final Logger log = LoggerFactory.getLogger(ThreeDes.class);

    public static String encryptEncode(String str){
        String fi = null;
        try {
            byte[] bytes = ThreeDes.encryptMode(str.getBytes(GlobalConf.CHAR_SET));
            String str1 = new String(bytes, GlobalConf.CHAR_SET);
            String str2 = URLEncoder.encode(str1, GlobalConf.CHAR_SET);
            fi = str2;
        }catch (Exception e){

        }
        return fi;
    }

    public static String decodeDecrypt(String str){
        String fi = null;
        try {
            String str1 = URLDecoder.decode(str, GlobalConf.CHAR_SET);
            byte[] bytes = ThreeDes.decryptMode(str1.getBytes(GlobalConf.CHAR_SET));
            String str2 = new String(bytes, GlobalConf.CHAR_SET);
            fi = str2;
        }catch (Exception e){

        }
        return fi;
    }
}
