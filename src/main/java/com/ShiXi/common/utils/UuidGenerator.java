package com.ShiXi.common.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Component
public class UuidGenerator {
    private static final String BASE62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String BASE64_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static final Map<String, Integer> BASE_TO_LENGHT_MAP = new HashMap<>();
    static {
        BASE_TO_LENGHT_MAP.put("BASE_64", 64);
        BASE_TO_LENGHT_MAP.put("BASE_62", 62);
    }
    public static final Map<String, String> BASE_TO_CHARACTERS_MAP = new HashMap<>();
    static {
        BASE_TO_CHARACTERS_MAP.put("BASE_64", BASE64_CHARACTERS);
        BASE_TO_CHARACTERS_MAP.put("BASE_62", BASE62_CHARACTERS);
    }
    /**
     * 生成8位Base62编码的UUID
     * @return 8位字符串，包含数字、大小写字母
     */
    public String generateUuid(Integer length,String baseType) {
        Integer base = BASE_TO_LENGHT_MAP.get(baseType);
        String baseCharacters = BASE_TO_CHARACTERS_MAP.get(baseType);
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(base);
            result.append(baseCharacters.charAt(randomIndex));
        }
        return result.toString();
    }

}
