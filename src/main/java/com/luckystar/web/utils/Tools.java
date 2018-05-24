package com.luckystar.web.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static String humpToLine(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static void humpToline(Pageable pageable){
        if(pageable==null||pageable.getSort()==null){
            return;
        }
        Class clazz = Sort.Order.class;
        try {
            Field field= clazz.getDeclaredField("property");
            field.setAccessible(true);
            pageable.getSort().forEach(new Consumer<Sort.Order>() {
                @Override
                public void accept(Sort.Order order) {
                    try {
                        field.set(order,humpToLine(order.getProperty()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
