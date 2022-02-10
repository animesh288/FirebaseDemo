package com.android.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.firebasedemo.databinding.ActivityDeleteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {

    ActivityDeleteBinding activityDeleteBinding;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDeleteBinding= DataBindingUtil.setContentView(this,R.layout.activity_delete);
        setContentView(activityDeleteBinding.getRoot());

        activityDeleteBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=activityDeleteBinding.username.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(DeleteActivity.this, "enter username", Toast.LENGTH_SHORT).show();
                }else{
                    deleteUser(username);
                }
            }
        });
    }

    private void deleteUser(String username) {
        reference= FirebaseDatabase.getInstance().getReference().child("users").child(username);
        Task<Void> task=reference.removeValue();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DeleteActivity.this, "user deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DeleteActivity.this, "user could not be deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}