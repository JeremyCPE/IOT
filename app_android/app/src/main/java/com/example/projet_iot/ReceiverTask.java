package com.example.projet_iot;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;


public class ReceiverTask extends AsyncTask<Void, byte[], Void> {
    private DatagramSocket UDPSocket;
    //private final String IP = "";
    private final int PORT;
    private ArrayList<TextView> altv;


    ReceiverTask(int port, ArrayList<TextView> altv) throws UnknownHostException, SocketException {
        this.UDPSocket = new DatagramSocket(port);
        this.PORT = port;
        this.altv = altv;
    }

/*    public void run() {
        //Looper.prepare();

        try {
            byte[] reset = "(0)".getBytes();
            //UDPSocket = new DatagramSocket();
            //(new ReceiverTask(this.UDPSocket, this.PORT)).execute();
            UDPSocket.send(new DatagramPacket(reset, reset.length, this.address, this.PORT));
            Log.d( "action", "reset");
            doInBackground();

        } catch (IOException e) {
            Log.d("network", e.toString());
        }

        //Looper.loop();
    }*/

    @Override
    protected Void doInBackground(Void... rien) {
        byte[] data = new byte [1024];
        while(true){

            Log.d("action","LaunchDoInBackground");
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                UDPSocket.receive(packet);
            } catch (IOException e) {
                Log.d("error",e.toString());
                e.printStackTrace();
            }
            int size = packet.getLength();
            data = packet.getData();
            String s_data = new String(data, 0, size);
            try {
                JSONObject j = new JSONObject(s_data);
            }catch (org.json.JSONException e){
                System.out.println(e);
            }

            Log.d("data", s_data);
            publishProgress(java.util.Arrays.copyOf(data, size));

        }
    }

    protected  void onProgessUpdate(byte[]... data){
        Log.println(Log.ASSERT, "progress", data.toString());
    }

    protected void changeTextViewValue(JSONObject jo){
        this.altv.get(0).setText(jo.toString());
    }
}