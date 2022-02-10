package com.android.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.firebasedemo.Model.User;
import com.android.firebasedemo.databinding.ActivityAddBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding activityAddBinding;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddBinding= DataBindingUtil.setContentView(this,R.layout.activity_add);
        setContentView(activityAddBinding.getRoot());

        reference= FirebaseDatabase.getInstance().getReference().child("users");


        activityAddBinding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=activityAddBinding.name.getText().toString();
                String contact=activityAddBinding.number.getText().toString();
                String username=activityAddBinding.username.getText().toString();
                addUser(username,name,contact);
            }
        });
    }

    private void addUser(String username,String name, String contact) {

        User user=new User(name,contact);
        reference.child(username).setValue(user);
        Toast.makeText(AddActivity.this, "added successfully", Toast.LENGTH_SHORT).show();

    }
}