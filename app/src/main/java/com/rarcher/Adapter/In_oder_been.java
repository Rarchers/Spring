package com.rarcher.Adapter;

/**
 * 进行中
 */

public class In_oder_been {
    String title;
    String start_time;
    String UID;

    public String getTitle() {
        return title;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getUID() {
        return UID;
    }

    public In_oder_been(String title, String start_time, String UID) {

        this.title = title;
        this.start_time = start_time;
        this.UID = UID;
    }
}
