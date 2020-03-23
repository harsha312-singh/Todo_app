package com.example.todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class input extends AppCompatActivity {

    private EditText editText,editText2;
    private Button button;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        button = findViewById(R.id.but_add);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("todo");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               save();
            }
        });
    }

    private void save(){
        final String task=editText.getText().toString();
        String status=editText2.getText().toString();
        if(TextUtils.isEmpty(status))
            status="pending";

        if(!TextUtils.isEmpty(task)){
            todo t = new todo(task,status);
            databaseReference.push().setValue(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(input.this,task+" is added",Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    editText2.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(input.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(input.this,"Enter the task",Toast.LENGTH_SHORT).show();
        }
    }
}
