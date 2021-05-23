package com.example.smarthome.Service;


import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class DBUtils {
    private static final String dbPath = "doors/data";
    private static final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(dbPath);

    public static DatabaseReference getRef() {
        return dbRef;
    }

    public static String getDbPath() {
        return dbPath;
    }
    public static void updateChild(Map<String, Object> data) {
        dbRef.updateChildren(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("UPDATE DOOR SUCCESS", "Update door data successfully! - Path " + dbPath);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e("UPDATE DOOR FAIL", "Update door data failed! - Path: " + dbPath);
                    }
                });
    }
}
