package com.github.diluka.crtticketprice.entity;


import java.util.HashMap;
import java.util.Map;

public class LineColors {
    private static final Map<String, Integer> catalog = new HashMap<>();

    static {
        catalog.put("1", 0xffff0000);
        catalog.put("2", 0xff00e600);
        catalog.put("3", 0xff0033ff);
        catalog.put("4", 0xffff8c1a);
        catalog.put("5", 0xff1af2ff);
        catalog.put("6", 0xffff4df2);
        catalog.put("6_expo", 0xffff4df2);
        catalog.put("7", 0xff99ffe6);
        catalog.put("8", 0xff8cff0d);
        catalog.put("9", 0xff8033ff);
        catalog.put("0", 0xffffd90f);
    }

    public static int getColor(String lineId) {
        return catalog.get(lineId);
    }
}
