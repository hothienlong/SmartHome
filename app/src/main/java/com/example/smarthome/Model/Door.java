package com.example.smarthome.Model;
import com.example.smarthome.R;

public class Door {
    private String doorName;
    private String doorType;
    private int doorStatus;
    private String doorTopic;
    private int doorImgId = R.drawable.door_close;

    public Door(String name, String type, int status, String topic) {
        doorName = name;
        doorType = type;
        doorTopic = topic;
        setDoorStatus(status);
    }

    public void setDoorName(String name) { doorName = name; }

    public void setDoorType(String type) { doorType = type; }

    public void setDoorStatus(int status) {

        doorStatus = status;
        if(doorStatus == 1 && doorType == "md")
            doorImgId = R.drawable.door_open;
        else if(doorStatus == 1 && doorType == "mw")
            doorImgId = R.drawable.win_open;
        else if(doorStatus == 0 && doorType == "md" )
            doorImgId = R.drawable.door_close;
        else if(doorStatus == 0 && doorType == "mw")
            doorImgId = R.drawable.win_close;
    }

    public void setDoorTopic(String topic) { doorTopic = topic; }
    public void setDoorImgId(int id) { doorImgId = id; }

    public  String getDoorName() { return doorName; }
    public  String getDoorType() { return doorType; }
    public int getDoorStatus() { return  doorStatus; }
    public String getDoorTopic() { return doorTopic; }
    public int getDoorImgId() { return doorImgId; }
}
