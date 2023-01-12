package com.example.vikamebli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    EditText etServerIP;
    EditText etServerPort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        etServerIP = findViewById(R.id.etServerIP);
        etServerPort = findViewById(R.id.etServerPort);
        etServerIP.setText(MainActivity.serverIP);
        etServerPort.setText(MainActivity.serverPort);
    }
}