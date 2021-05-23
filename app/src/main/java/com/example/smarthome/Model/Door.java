package com.example.smarthome.Model;
import android.util.Log;

import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.List;

public class Door {
    public static  final String topic = "bbc-door";
    public final static List<Door> initList = new ArrayList<Door>();
    public static int index ;

    private String doorName;
    private String doorType;
    private String doorStatus;
    private int doorImgId = R.drawable.door_close;

    public Door(String name, String type, String status, String topic) {
        doorName = name;
        doorType = type;
        setDoorStatus(status);

        initList.add(this);
        index = initList.size();
    }

    public Door setDoorName(String name) { doorName = name; return this; }

    public Door setDoorType(String type) { doorType = type; return this; }

    public Door setDoorStatus(String status) {

        doorStatus = status;
        if(doorStatus.equals("1") && doorType.equals("md"))
            doorImgId = R.drawable.door_open;
        else if(doorStatus.equals("1") && doorType.equals("mw")){
            doorImgId = R.drawable.win_open;
        }
        else if(doorStatus.equals("0") && doorType.equals("md") )
            doorImgId = R.drawable.door_close;
        else if(doorStatus.equals("0") && doorType.equals("mw"))
            doorImgId = R.drawable.win_close;

        return this;
    }

    public Door setDoorImgId(int id) { doorImgId = id; return this; }

    public  String getDoorName() { return doorName; }
    public  String getDoorType() { return doorType; }
    public  String getDoorStatus() { return  doorStatus; }
    public int getDoorImgId() { return doorImgId; }

}
