package com.zzj.springboot.util;

import java.util.Random;

/**
 * Created by zzj on 2020/6/5.
 */
public class StringUtils {
    public static String getRandomString(int length){

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
