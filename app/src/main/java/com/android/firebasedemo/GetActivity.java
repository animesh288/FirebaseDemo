package com.android.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.firebasedemo.databinding.ActivityGetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetActivity extends AppCompatActivity {

    ActivityGetBinding activityGetBinding;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGetBinding= DataBindingUtil.setContentView(this,R.layout.activity_get);
        setContentView(activityGetBinding.getRoot());

        activityGetBinding.getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=activityGetBinding.username.getText().toString();
                if(username.length()==0){
                    Toast.makeText(GetActivity.this, "enter username", Toast.LENGTH_SHORT).show();
                }else{
                    getData(username);
                }
            }
        });
    }

    private void getData(String username) {
        reference=FirebaseDatabase.getInstance().getReference().child("users");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful() || !task.getResult().exists()){
                    Toast.makeText(GetActivity.this, "user not found", Toast.LENGTH_SHORT).show();
                }else{
                    DataSnapshot snapshot=task.getResult();
                    String name=snapshot.child("name").getValue().toString();
                    String contact=snapshot.child("contact").getValue().toString();

                    activityGetBinding.contact.setText(contact);
                    activityGetBinding.name.setText(name);
                }
            }
        });
    }
}