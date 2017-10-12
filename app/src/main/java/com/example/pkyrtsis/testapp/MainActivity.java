package com.example.pkyrtsis.testapp;

import android.net.nsd.NsdServiceInfo;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static android.R.attr.port;
import static com.example.pkyrtsis.testapp.R.id.editText;
import static com.example.pkyrtsis.testapp.R.id.textView;

public class MainActivity extends AppCompatActivity {

    NsdHelper mNsdHelper;
    NsdServiceInfo serviceFound;
    ServerSocket mSocket;
    private int mPort = -1;

    public static final String TAG = "NsdChat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, Nsd.class);

        mNsdHelper = new NsdHelper(this);
        mNsdHelper.initializeNsd();
    }


    public void updateMessageArea(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
        //sendMessage(message);
    }
/*
    public void sendMessage(String msg) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://" +
        }
    }
*/
    public void printMessage(String msg) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(msg);
    }

    public void registerService(View view) {
        initializeSocket();
        String msg = mNsdHelper.registerService(mPort);
        printMessage(msg);
    }

    public void discoverServices(View view) {
        String msg = mNsdHelper.discoverServices();
        printMessage(msg);
    }

    public void resolveService(View view) {
        NsdServiceInfo info;
        info = mNsdHelper.resolveService();
        printMessage("resolved: " + info);
    }

    public int getPort() { return mPort; }
    public void setPort(int port) {
        mPort = port;
    }

    public void initializeSocket() {
        // Initialize a server socket on the next available port
        try {
            mSocket = new ServerSocket(0);
        }
        catch(IOException e) {
            Log.e(TAG, "Error creating ServerSocket: ", e);
            e.printStackTrace();
        }

        // Store the chosen port
        mPort = mSocket.getLocalPort();
        Log.d(TAG, "mPort: " + mPort);
    }

    public void tearDown() {
        mNsdHelper.tearDown();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (mNsdHelper != null)
            //smNsdHelper.discoverServices();
    }

    @Override
    protected void onPause() {
        if (mNsdHelper != null)
            mNsdHelper.stopServiceDiscovery();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mNsdHelper.tearDown();
        super.onDestroy();
    }



}


