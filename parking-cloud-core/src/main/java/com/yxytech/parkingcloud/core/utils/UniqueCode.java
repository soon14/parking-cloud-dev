package com.yxytech.parkingcloud.core.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UniqueCode {
    private static String shuffleString(String str) {
        List<Character> characters = new ArrayList<Character>();

        for(char c : str.toCharArray()){
            characters.add(c);
        }

        StringBuilder output = new StringBuilder(str.length());

        while(characters.size() != 0){
            int randPicker = (int) (Math.random() * characters.size());

            output.append(characters.remove(randPicker));
        }

        return output.toString();
    }

    public static synchronized String generateUniqueCode(Integer random) {
        Date date = new Date();

        return shuffleString(Long.toString(date.getTime() << 10 | random % 1024, 36));
    }

    public static synchronized String generateId(StringRedisTemplate redisTemplate, String keyName) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        keyName = dateFormat.format(date) + keyName;
        Long id = redisTemplate.opsForValue().increment(keyName, 1);

        return format.format(date) + String.format("%05d", id);
    }
}
