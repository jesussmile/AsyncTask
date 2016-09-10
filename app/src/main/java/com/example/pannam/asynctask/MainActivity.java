package com.example.pannam.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnParse;
    ListView listApps;
String xmlData;
   // TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       //  mTextView =(TextView)findViewById(R.id.textView1);
      //  new DownloadData().execute("http://202.70.77.195/schedule/schedule.txt");

        btnParse = (Button)findViewById(R.id.btnParse);
        listApps = (ListView)findViewById(R.id.listApps);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseApplication parseApplication = new ParseApplication(xmlData);
                boolean operationStatus = parseApplication.process();


            }
        });

      new DownloadData().execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=25/xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        String myXmlData;

        @Override
        protected String doInBackground(String... params) {
            try {
                myXmlData = downloadXML(params[0]);

            } catch (IOException e) {
                return "Unable to download XML file";
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("OnPostExecute", myXmlData);
            //mTextView.setText(myXmlData);
            xmlData = myXmlData;
        }
    }


    private String downloadXML(String theUrl) throws IOException {
        int BUFFER_SIZE = 2000;
        InputStream is = null;
        String xmlContents = "";
        try {

            URL url = new URL(theUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            int response = conn.getResponseCode();
            Log.d("DownloadXML ", "Response " + response);
            is = conn.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);

            int charRead;
            char[] inputBuffer = new char[BUFFER_SIZE];
            try {
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    xmlContents += readString;
                    inputBuffer = new char[BUFFER_SIZE];
                }

                return xmlContents;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } finally {
            //close inputstream , clear memory
            if (is != null)
                is.close();
        }


    }
}
