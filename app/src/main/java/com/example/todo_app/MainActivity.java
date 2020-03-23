package com.example.todo_app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.editText);
    }
    public void fun(View v)
    {

        String phoneNo="+91"+editText.getText().toString();
        if(phoneNo.length()<=12){
            Toast.makeText(MainActivity.this,"Enter correct number",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, phoneNo, Toast.LENGTH_SHORT).show();
            Intent i= new Intent(MainActivity.this, otp_page.class);
            i.putExtra("ph", phoneNo);
            startActivity(i);
        }
    }
}
