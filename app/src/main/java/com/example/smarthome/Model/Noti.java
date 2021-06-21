package com.example.smarthome.Model;

import android.content.Intent;

import java.sql.Time;

public class Noti {
    public String mContent;
    public String mMoment;
    public Integer mSeen;
    public String mType;

    public Noti() {
        // mặc định của firebase, khi nhận data
    }

    public Noti(String mContent, String mMoment, Integer mSeen, String mType) {
        this.mContent = mContent;
        this.mMoment = mMoment;
        this.mSeen = mSeen;
        this.mType = mType;
    }
}
