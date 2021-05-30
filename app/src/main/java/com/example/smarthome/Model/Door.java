package com.example.smarthome.Model;
import android.util.Log;

import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Door {
    public static  final String topic = "bbc-door";
    public final static HashMap<String, Door> initHash = new HashMap<String, Door>();
    public final static List<Door> initList = new ArrayList<Door>();
    public static int index ;

    private String doorName;
    private String doorType;
    private Boolean doorStatus;
    private int doorImgId = R.drawable.door_close;

    public Door(String name, String type, Boolean status) {
        doorName = name;
        doorType = type;
        setDoorStatus(status);
        //Door.initList.add(this);
        index = initList.size();
    }

    public Door setDoorName(String name) { doorName = name; return this; }

    public Door setDoorType(String type) { doorType = type; return this; }

    public Door setDoorStatus(Boolean status) {

        doorStatus = status;
        if(doorStatus && doorType.equals("door"))
            doorImgId = R.drawable.door_open;
        else if(doorStatus && doorType.equals("window")){
            doorImgId = R.drawable.win_open;
        }
        else if(!doorStatus && doorType.equals("door") )
            doorImgId = R.drawable.door_close;
        else if(!doorStatus && doorType.equals("window"))
            doorImgId = R.drawable.win_close;

        return this;
    }

    public Door setDoorImgId(int id) { doorImgId = id; return this; }

    public  String getDoorName() { return doorName; }
    public  String getDoorType() { return doorType; }
    public  Boolean getDoorStatus() { return  doorStatus; }
    public int getDoorImgId() { return doorImgId; }

}
