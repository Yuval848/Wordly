package com.example.wordly_by_yuvalmiz;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FbModule {

    private MainActivity mainActivity;
    public FbModule(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("color");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String str = snapshot.getValue(String.class);
                if(str != null)
                {
                    mainActivity.setBackgroundColor(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        }) ;

    }

    public void changeBackgroundColorInFireBase(String str){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("color");
        reference.setValue(str);

    }
}

