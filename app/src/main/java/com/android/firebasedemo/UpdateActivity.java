package com.android.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.firebasedemo.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding activityUpdateBinding;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateBinding= DataBindingUtil.setContentView(this,R.layout.activity_update);
        setContentView(activityUpdateBinding.getRoot());

        activityUpdateBinding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=activityUpdateBinding.name.getText().toString();
                String contact=activityUpdateBinding.number.getText().toString();
                String username=activityUpdateBinding.username.getText().toString();
                updateUser(username,name,contact);
            }
        });
    }

    private void updateUser(String username, String name, String contact) {
        reference= FirebaseDatabase.getInstance().getReference().child("users");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.getResult().exists()) Toast.makeText(UpdateActivity.this, "user not found", Toast.LENGTH_SHORT).show();
                else{
                    Task<Void> task1=reference.child(username).child("name").setValue(name);
                    Task<Void> task2=reference.child(username).child("contact").setValue(contact);
                    if(task1.isSuccessful() && task2.isSuccessful()){
                        Toast.makeText(UpdateActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}