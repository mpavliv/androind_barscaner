package com.example.vikamebli;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vikamebli.db.SN;
import com.example.vikamebli.db.TableSNDbManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadSN extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context...contexts) {
        String taskURL = "http://" + MainActivity.serverIP + ":" + MainActivity.serverPort + "/sn";
        List<String> res = new ArrayList<String>();
        //StringBuilder result = new StringBuilder();
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(taskURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    res.add(line);
                    line = bufferedReader.readLine();
                }
                Log.i("MYLT","Ended loading from server");

                TableSNDbManager snDbManager = new TableSNDbManager(contexts[0]);
                snDbManager.openDb();
                snDbManager.deleteAllDB();
                int i = 0;
                int len = res.size();
                for (String s : res) {
                    Log.i("MYLT","Adding record: " + i + " from " + len +"  "+ s);
                    i++;
                    SN sn = new SN(s);
                    snDbManager.insertDB(sn);
                }
                snDbManager.closeDB();
            } else {
                // ошибка
                Log.i("MYLT", "connection error");
            }
        } catch (MalformedURLException e) {
            Log.i("MYLT",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("MYLT",e.toString());
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}

