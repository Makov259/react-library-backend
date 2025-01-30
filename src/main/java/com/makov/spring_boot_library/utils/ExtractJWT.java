package com.makov.spring_boot_library.utils;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String extraction) {
        token.replace("Bearer ", "");

        String[] accessTokenChunks = token.split("\\."); //saving the 3 parts of the JWT
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(accessTokenChunks[1]));//decoding the 2nd part - Payload
        String[] entries = payload.split(","); // getting the pairs like "sub": "Email@email.com"
        Map<String, String> map = new HashMap<>();

        for (String entry : entries) {
            //splitting the current entry  keyValue[0]="sub" and keyValue[1]="Email@email.com e.g."
            String[] keyValue = entry.split(":");

            if(keyValue[0].equals(extraction)) {
                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }
        }
        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }
        return null;
    }
}
