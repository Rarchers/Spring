package com.rarcher.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.rarcher.Login_Register.LoginActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by 25532 on 2019/3/2.
 */

public class LocalDB extends SQLiteOpenHelper {

    public static final String Creat_Book = "create table Users ("

            + "id integer primary key autoincrement," +
            "phone text," +
            "password text," +
            "image Blob,"+
            "identitycode text,"+//身份证
            "sex text," + //sex   male->男   female->女
            "name text) ";


    private Context context;
    public LocalDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Creat_Book);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Users");
        onCreate(db);
    }

    //查找本用户名,返回一个Uer类型
    public static Users query_user(String phone,LocalDB dbhelper) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("Users", null, "phone = ?", new String[]{phone}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String un = phone;
                String pw = cursor.getString(cursor.getColumnIndex("password"));
                String n = cursor.getString(cursor.getColumnIndex("name"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String id = cursor.getString(cursor.getColumnIndex("identitycode"));
                ByteArrayInputStream stream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex("image")));
                Users user = new Users(un, pw, n, sex,id,stream);
                return user;
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return null;


    }

    //更新当前用户的密码
    public static void updata_user(String phone, String newpass,LocalDB dbhelper) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newpass);
        db.update("Users", values, "phone = ?", new String[]{phone});
    }

    //TODO:暂时只进行本地注册
    public static void insert_user(Context context,String phone, String password, String name, String sex,String id,Bitmap bmp,LocalDB dbhelper) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os);

        values.put("phone", phone);
        values.put("password", password);
        values.put("identitycode",id);
        values.put("name", name);
        values.put("sex", sex);
        values.put("image",os.toByteArray());
        db.insert("Users", null, values);
        Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
    }

}