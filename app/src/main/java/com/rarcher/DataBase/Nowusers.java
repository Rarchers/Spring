package com.rarcher.DataBase;

/**
 * Created by 25532 on 2019/3/3.
 */

public class Nowusers {

   static String phone;
   static String name;

    private static Nowusers instance = null;

    public static synchronized Nowusers getInstance() {
        // 这个方法比上面有所改进，不用每次都进行生成对象，只是第一次
        // 使用时生成实例，提高了效率！
        if (instance == null)
            instance = new Nowusers();
        return instance;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {

        Nowusers.name = name;
    }

    public static void setPhone(String phone) {
        Nowusers.phone = phone;
    }
}
