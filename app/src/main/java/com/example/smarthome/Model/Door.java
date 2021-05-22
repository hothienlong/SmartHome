package com.example.smarthome.Model;
import android.util.Log;

import com.example.smarthome.R;

public class Door {
    private String doorName;
    private String doorType;
    private String doorStatus;
    private String doorTopic;
    private int doorImgId = R.drawable.door_close;

    public Door(String name, String type, String status, String topic) {
        doorName = name;
        doorType = type;
        doorTopic = topic;
        setDoorStatus(status);
    }

    public void setDoorName(String name) { doorName = name; }

    public void setDoorType(String type) { doorType = type; }

    public void setDoorStatus(String status) {

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
    }

    public void setDoorTopic(String topic) { doorTopic = topic; }
    public void setDoorImgId(int id) { doorImgId = id; }

    public  String getDoorName() { return doorName; }
    public  String getDoorType() { return doorType; }
    public  String getDoorStatus() { return  doorStatus; }
    public  String getDoorTopic() { return doorTopic; }
    public int getDoorImgId() { return doorImgId; }
}
