package com.sudharshan.user.anasnmnt16_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//created main class which extends AppCompatActivity
//When an Android application is first started, the runtime system creates a
// single thread called Main Thread in which all application components will run by default
public class MainActivity extends AppCompatActivity {

    // insert UI components
    EditText editText;
    Button button1;
    TextView textView;
    Button button2;

    //Oncreate method is called when main activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//implementing  UI components
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView1);


        //here it is to ask permission for the Write external storage to allow or denny them
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(permissions, 1);
        }
//then in case the permission is allow which is one the layout file which is from .xml file will be shown
        //by  these set onclicklistener if we click ok add data button it adds them
        //if we click on delete button it shows the file is deleted or else the file notfound as a toast
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Mytask().execute();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletefile();
            }
        });
    }
    @Override
//here it is to ask for the runtime permissions  to allow or denny them
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                break;
        }
    }

    //here creating a method called writeexternal storage
    public void writeexternalstorage(){
        //where taking a string type variable state
        String state ;

        //to check wheater the mounted the External storage state or to created the it
        state = Environment.getExternalStorageState();
        Log.d("SDCARD",state);//showing that a mount is there
        Toast.makeText(this, ""+state, Toast.LENGTH_SHORT).show();
        //if the media mounted is equals to state
        if(Environment.MEDIA_MOUNTED.equals(state)){
            //then file directory will be created, creating a path for the Myapp file
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File (Root.getAbsolutePath()+"/MyAppFile");
            if(!Dir.exists()){ //if it doesnt exixts to make dir in the virtual device
                Dir.mkdir();
            }
            //now creatign file object for the dir called MyMessage.txt
            File file  = new File(Dir,"MyMessage.txt");
            //now get the edittext to the message
            String message = editText.getText().toString();
            try {
                //now creating fileotputstream object for the file to write the text in the edit text
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                //outputstream.will write msg and get to the getbytes
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                //it sets to edit text
                editText.setText("");Toast.makeText(this, "Message Saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{//it the external storage not exits it shows the toast
            Toast.makeText(this, "External Storage Not Mounted", Toast.LENGTH_SHORT).show();
        }

    }



    //here taking read extenal storage method
    public void readexternalstoragee(){
        //creating an dir tfor the file to read the text using file inputstream
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File (Root.getAbsolutePath()+"/MyAppFile");
        File file  = new File(Dir,"MyMessage.txt");
        String message;
        try {
            FileInputStream fileinputstream = new FileInputStream(file);
            InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            StringBuffer stringbuffer = new StringBuffer();
            while((message = bufferedreader.readLine())!=null){
                stringbuffer.append(message+"\n");

            }
            textView.setText(stringbuffer.toString());
            textView.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //this method is to deletfile from the Myapp dir of Mymessage.txt file
    public void deletefile(){
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File (Root.getAbsolutePath()+"/MyAppFile");
        File file  = new File(Dir,"MyMessage.txt");
        if(Dir.exists()){//if dir exits on clicking the delete button it will be showing the the toast
            boolean deleted = file.delete();
            Toast.makeText(this, "File Deleted"+deleted, Toast.LENGTH_SHORT).show();
        }
        else{//or else it displays the following toast
            Toast.makeText(this, "File Not found", Toast.LENGTH_SHORT).show();
        }

    }//implementing the methods in the asynctask to run the methods in the doinback ground method
    public class Mytask extends AsyncTask<Void,Void,String> {

        //this method runs on UI thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //this method run in seperate thread
        @Override
        protected String doInBackground(Void... voids) {

            //do task getting information from web
            return null;
        }


        //run on UI thread
        @Override
        protected void onPostExecute(String o) {
            //do something with data like update data
            super.onPostExecute(o);
            writeexternalstorage();
            readexternalstoragee();
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

        }

    }
}
