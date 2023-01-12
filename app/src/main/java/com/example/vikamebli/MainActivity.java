package com.example.vikamebli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vikamebli.db.SN;
import com.example.vikamebli.db.TableSNDbManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button buttonProduction;
    public static String serverIP = "185.65.246.123";
    public static String serverPort = "8080";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonProduction = findViewById(R.id.buttonProduction);
        buttonProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityProduction();
            }
        });
    }

    public void openActivityProduction() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void shipment(View view) {
        List<SN> list = new ArrayList<>();
        TableSNDbManager snDbManager = new TableSNDbManager(this);
        snDbManager.openDb();
        list = snDbManager.readDB("001774221");
        Log.i("MYLT","Readed " + list.size() + " records");
        for (SN sn : list) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    sn.product, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void openActivityControl(View view) {
        Intent intent = new Intent(this, ControlActivity.class);
        startActivity(intent);
    }


    public void loadDictionary(View view) {
        DownloadSN task = new DownloadSN();
        task.execute(this);
    }

    public void Settings(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}