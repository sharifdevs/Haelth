package com.example.haelth;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONParser extends AsyncTask<Void,Void,Void> {
    String url = "";
    String data = "";
    String parsedData = "";
    public static JSONObject jsonObject;

    // constructor
    public JSONParser(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            InputStream inputStream;
            BufferedReader bufferedReader;
            if(url.charAt(0)=='h' && url.charAt(1)=='t' && url.charAt(2)=='t' && url.charAt(3)=='p') {
                URL urll = new URL(this.url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urll.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            } else {
                File jFile = new File(App.context.getFilesDir(), "/info");
                bufferedReader = new BufferedReader(new FileReader(jFile));
            }

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
            parsedData += data;
            try {
                JSONObject jObj = new JSONObject(parsedData);
                this.jsonObject = jObj;
            } catch (JSONException e) {e.printStackTrace();}

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveJsonToCache() {
        String filename = "info";
        String fileContents = App.userObj.toString();;
        FileOutputStream outputStream;
        try {
            outputStream = App.context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // ...
    }
}
